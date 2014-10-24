import xml.etree.ElementTree as Et

from django.http import HttpResponse
from django.shortcuts import render, get_object_or_404
from django.views import generic

from cs242_svn.models import Project, SVNFile


class IndexView(generic.ListView):
    """
    View for the index page
    displays a list of all projects
    """
    template_name = 'cs242_svn/index.html'
    context_object_name = 'project_list'

    def get_queryset(self):
        """
        Return the list of projects
        """
        return Project.objects.all()


class FileListView(generic.ListView):
    """
    View for showing a list of files
    (doesn't work, not currently in use)
    """
    template_name = 'cs242_svn/detail.html'
    context_object_name = 'file_list'
    model = SVNFile

    def get_queryset(self):
        return SVNFile.objects.all()


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


def file_view(request, file_id):
    """
    Not in use, but possible view for displaying file info
    :param request: Http request
    :param file_id: reference to a file
    :return: details about a specific file
    """
    return HttpResponse("You're looking at %s" % file_id)


def parse_xml(request):
    """
    Method called once to populate db.sqlite3
    Parses the 2 xml docs in Portfolio and generates projects
    and their corresponding files
    :param request: Http request
    :return: 'XML parsed' if parsing was successful
    """

    # list of all directories and files
    list_tree = Et.parse('svn_list.xml')
    list_root = list_tree.getroot().find('list')

    # empty hash of revision numbers to projects
    projects = {}
    # empty list of files
    files = []
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
