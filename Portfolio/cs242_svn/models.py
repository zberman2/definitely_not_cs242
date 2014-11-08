from django.db import models


class Project(models.Model):
    """
    template for a project
    contains a name, date, description and revision number
    """
    project_name = models.CharField(max_length=100)
    date = models.CharField(max_length=50)
    description = models.CharField(max_length=200)
    revision = models.CharField(max_length=10)

    # to string method
    def __str__(self):
        return self.project_name


class SVNFile(models.Model):
    """
    template for a file
    contains a project reference, path, name, size,
    revision number, author name and date
    """
    project = models.ForeignKey(Project)
    path = models.CharField(max_length=200)
    name = models.CharField(max_length=100)
    size = models.CharField(max_length=10)
    revision = models.CharField(max_length=10)
    author = models.CharField(max_length=50)
    date = models.CharField(max_length=50)

    # to string method
    def __str__(self):
        return self.name


class Message(models.Model):
    """
    template for a message post within the portfolio
    contains a message text, date tag and a parent
    """
    text = models.CharField(max_length=240)
    date = models.DateTimeField(auto_now_add=True)
    parent_id = models.ForeignKey('self', null=True, blank=True)

    # to string method
    def __str__(self):
        return self.text