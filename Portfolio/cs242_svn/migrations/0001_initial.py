# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Project',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('project_name', models.CharField(max_length=100)),
                ('date', models.DateField(verbose_name=b'date submitted')),
                ('version', models.DecimalField(max_digits=2, decimal_places=1)),
                ('description', models.CharField(max_length=240)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='SVN_File',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('path', models.CharField(max_length=200)),
                ('project', models.ForeignKey(to='cs242_svn.Project')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
    ]
