__author__ = 'Zack Berman'

from django.conf.urls import patterns, url

from cs242_svn import views

urlpatterns = patterns('',
    # index url ex: localhost:8000/cs242_svn/
    url(r'^$', views.IndexView.as_view(), name='index'),
    # file list url ex: localhost:8000/cs242_svn/1/
    url(r'^(?P<project_id>\d+)/$', views.file_list_view, name='detail'),
    # url(r'^(?P<pk>\d+)/$', views.FileListView.as_view(), name='detail'),

    # parse xml link: localhost:8000/cs242_svn/parse_xml
    url(r'^parse_xml/$', views.parse_xml, name='parse_xml')
)