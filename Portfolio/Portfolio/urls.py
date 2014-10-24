from django.conf.urls import patterns, include, url
from django.contrib import admin

urlpatterns = patterns('',
                       # cs242_svn url
                       url(r'^cs242_svn/', include('cs242_svn.urls', namespace="cs242_svn")),
                       url(r'^admin/', include(admin.site.urls)),
)
