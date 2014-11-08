import xml.etree.ElementTree as Et

from django.http import HttpResponse
from django.shortcuts import render, get_object_or_404, redirect

from cs242_svn.forms import MessageForm
from cs242_svn.models import Project, SVNFile, Message

# list of banned words
banned = ['shit', 'piss', 'fuck', 'cunt', 'cocksucker', 'motherfucker', 'tits']


def index_view(request):
    """
    Method called to invoke the index page
    Populates a list of projects and messages to be placed
    in the page (plus the form for submitting a comment)
    :param request: Empty request
    :return: render the index page with the projects, messages and form
    """
    projects = Project.objects.all()
    messages = get_messages(None)
    form = MessageForm
    context = {'projects': projects, 'comments': messages, 'form': form}
    return render(request, 'cs242_svn/index.html', context)


def file_list_view(request, project_id):
    """
    Currently used for displaying a list of files
    :param request: Http request
    :param project_id: reference to the project
    :return: a list of files corresponding to that project
    """
    project = get_object_or_404(Project, pk=project_id)
    file_list = SVNFile.objects.filter(project=project)
    return render(request, 'cs242_svn/detail.html', {'file_list': file_list, 'project': project})


def post_message(request):
    """
    Function called when the user submits a new comment
    Finds the parent comment, formats the comment and saves it
    :param request: Http request with message and parent info
    :return: redirect to the index page
    """
    # get message text
    message = request.POST['text']
    # get parent message text
    parent = request.POST['parent-text']
    parent_comment = None
    if parent != '':
        # set parent comment if there is one
        parent_comment = Message.objects.get(text=parent)

    # create a list of separate words in the message
    words = message.split(" ")
    for index, word in enumerate(words):
        if word.lower() in banned:
            # replace with grawlix if a banned word
            words[index] = '@#$%&!'

    # rejoin words and eliminate leading whitespace
    message = " ".join(words)
    # cut off leading whitespace
    message = message.lstrip(' ')

    # add leading whitespace depending on the depth of the comment
    depth = calculate_depth(parent_comment)
    for i in range(0, depth):
        message = "\t" + message

    # save new message and redirect to index page
    Message(text=message, parent_id=parent_comment).save()
    return redirect('/cs242_svn/')


def calculate_depth(comment):
    """
    Returns the depth of the comment in the tree of comments
    :param comment parent of the node to calculate the depth
    :return: depth of the tree that the comment resides in
    """
    # by definition, null parent indicates the root (root is level 0)
    if comment is None:
        return 0
    # recurse and add 1
    return 1 + calculate_depth(comment.parent_id)


def get_messages(parent_id):
    """
    Recursively populates a list of messages by date, but allows for
    nested comments
    :param parent_id comment in level above current level (initially None)
    :return: list of messages in order of when they were posted
    """
    message_list = []
    # get list of all messages on current level
    current_message_list = Message.objects.filter(parent_id=parent_id)

    # we've reached the bottom
    if len(current_message_list) == 0:
        return current_message_list

    # append messages to main list and recurse to next level
    for message in current_message_list:
        message_list.append(message)
        sub_message_list = get_messages(message.id)
        if sub_message_list:
            message_list += sub_message_list

    return message_list


# def parse_xml(request):
# """
# Method called once to populate db.sqlite3
# Parses the 2 xml docs in Portfolio and generates projects
#     and their corresponding files
#     :param request: Http request
#     :return: 'XML parsed' if parsing was successful
#     """
#
#     # empty hash of revision numbers to projects
#     projects = {}
#     # empty list of files
#     files = []
#
#     # look through list for projects and files
#     parse_xml_list(projects, files)
#
#     # look through log for commit messages
#     parse_xml_log(projects, files)
#
#     return HttpResponse("XML parsed")


def parse_xml(request):
    """
    Method called once to populate db.sqlite3
    Parses the 2 xml docs in Portfolio and generates projects
    and their corresponding files
    :param request: Http request
    :return: 'XML parsed' if parsing was successful
    """

    # empty hash of revision numbers to projects
    projects = {}
    # empty list of files
    files = []

    # look through list for projects and files
    list_tree = Et.parse('svn_list.xml')
    list_root = list_tree.getroot().find('list')

    for entry in list_root.findall('entry'):
        path = entry.find('name').text
        if entry.attrib['kind'] == 'dir' and path.count('/') == 0:
            # found a project!
            commit = entry.find('commit')
            revision = commit.attrib['revision']
            date = commit.find('date').text
            projects[revision] = Project(project_name=path, date=date, description="",
                                         revision=revision)
        elif entry.attrib['kind'] == 'file':
            # found a file!
            size = entry.find('size').text
            commit = entry.find('commit')
            revision = commit.attrib['revision']
            date = commit.find('date').text
            author = commit.find('author').text
            last_slash = path.rfind('/')
            name = path[last_slash + 1:]
            files.append(SVNFile(path=path, name=name, size=size,
                                 revision=revision, author=author,
                                 date=date))

    # look through log for commit messages
    log_tree = Et.parse('svn_log.xml')
    log_root = log_tree.getroot()
    for log_entry in log_root.findall('logentry'):
        revision = log_entry.attrib['revision']
        if revision in projects:
            # set description of project
            projects[revision].description = log_entry.find('msg').text

    # save projects in the database
    for key in projects:
        item = projects[key]
        item.save()

    # set project foreign keys
    for f in files:
        revision = f.revision
        try:
            project = Project.objects.get(revision=revision)
        except Project.DoesNotExist:
            project = None
        if project:
            f.project = project
            f.save()

    return HttpResponse("XML parsed")


def parse_xml_list(projects, files):
    """
    parse the svn_list.xml file and create files and projects
    to be placed in the data base
    :param projects: hash of revision numbers to projects (initially empty)
    :param files: list of files (initially empty)
    :return: None
    """
    # list of all directories and files
    list_tree = Et.parse('svn_list.xml')
    list_root = list_tree.getroot().find('list')

    for entry in list_root.findall('entry'):
        path = entry.find('name').text
        if entry.attrib['kind'] == 'dir' and path.count('/') == 0:
            # found a project!
            commit = entry.find('commit')
            revision = commit.attrib['revision']
            date = commit.find('date').text
            projects[revision] = Project(project_name=path, date=date, description="",
                                         revision=revision)
        elif entry.attrib['kind'] == 'file':
            # found a file!
            size = entry.find('size').text
            commit = entry.find('commit')
            revision = commit.attrib['revision']
            date = commit.find('date').text
            author = commit.find('author').text
            last_slash = path.rfind('/')
            name = path[last_slash + 1:]
            files.append(SVNFile(path=path, name=name, size=size,
                                 revision=revision, author=author,
                                 date=date))


def parse_xml_log(projects, files):
    """
    parse the svn_log.xml file for commit messages
    link files back their projects
    :param projects: hash of revision numbers to projects
    :param files: list of files
    :return: None
    """
    log_tree = Et.parse('svn_log.xml')
    log_root = log_tree.getroot()
    for log_entry in log_root.findall('logentry'):
        revision = log_entry.attrib['revision']
        if revision in projects:
            # set description of project
            projects[revision].description = log_entry.find('msg').text

    # save projects in the database
    for key in projects:
        item = projects[key]
        item.save()

    # set project foreign keys
    for f in files:
        revision = f.revision
        try:
            project = Project.objects.get(revision=revision)
        except Project.DoesNotExist:
            project = None
        if project:
            f.project = project
            f.save()