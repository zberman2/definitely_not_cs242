# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('cs242_svn', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='SVNFile',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('path', models.CharField(max_length=200)),
                ('name', models.CharField(max_length=100)),
                ('size', models.IntegerField(max_length=10)),
                ('commit', models.IntegerField(max_length=10)),
                ('author', models.CharField(max_length=50)),
                ('date', models.DateTimeField(verbose_name=b'date submitted')),
                ('project', models.ForeignKey(to='cs242_svn.Project')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.RemoveField(
            model_name='svn_file',
            name='project',
        ),
        migrations.DeleteModel(
            name='SVN_File',
        ),
        migrations.RemoveField(
            model_name='project',
            name='version',
        ),
    ]
