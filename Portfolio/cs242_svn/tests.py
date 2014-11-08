from django.test import TestCase
from django.core.urlresolvers import reverse

from cs242_svn.models import Message, SVNFile, Project


def create_project(name):
    """
    Method which creates a new project and adds it to the database
    For testing purposes, we only care about its name for now
    """
    return Project.objects.create(project_name=name, date="", description="project", revision=20)


def create_file(name, project):
    return SVNFile.objects.create(project=project, path='', name=name, size='10',
                                  revision='10', author='zberman2', date='')


def create_message(text, parent):
    return Message.objects.create(text=text, parent_id=parent)


class ProjectTests(TestCase):
    def test_index_view_with_no_projects(self):
        """
        Tests that a hardcoded string appears when no projects
        are present in the database
        """
        response = self.client.get(reverse('cs242_svn:index'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "No projects are available.")

    def test_add_project(self):
        """
        Tests that a project appears in the index page when there
        are projects in the database
        """
        create_project("test")
        response = self.client.get(reverse('cs242_svn:index'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "test")


class SVNFileTests(TestCase):
    def test_file_list_view(self):
        """
        Tests that a hardcoded string appears in a project's list of
        files when it contains no files
        """
        project = create_project('test')
        response = self.client.get(reverse('cs242_svn:detail',
                                           args=(project.id,)))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "No files are available.")

    def test_add_file_to_project(self):
        """
        Tests tha files show up in the project detail page when
        it does contain files
        """
        project = create_project('test')
        create_file('test_file', project)
        response = self.client.get(reverse('cs242_svn:detail',
                                           args=(project.id,)))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "test_file")


class MessageTests(TestCase):
    def test_index_view_with_no_messages(self):
        """
        Tests that a hardcoded string appears when no comments
        are present in the database
        """
        response = self.client.get(reverse('cs242_svn:index'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "No comments are available.")

    def test_index_view_add_comment(self):
        """
        Tests that a comment appears in the index page when there
        are comments in the database
        """
        create_message("test", None)
        response = self.client.get(reverse('cs242_svn:index'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "test")

    def test_nested_comments(self):
        """
        Tests that comments are nested and children point to their
        parent comments
        """
        parent = create_message("parent comment", None)
        child = create_message("child comment", parent)
        assert child.parent_id == parent

    # def test_censor(self):
    #     """
    #     Tests that swear words are censored
    #     """
    #     create_message("Fuck", None)
    #     response = self.client.get(reverse('cs242_svn:index'))
    #     self.assertEqual(response.status_code, 200)
    #     self.assertContains(response, "@#$%&!")