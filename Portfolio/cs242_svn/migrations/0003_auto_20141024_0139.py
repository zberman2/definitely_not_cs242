# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import datetime


class Migration(migrations.Migration):

    dependencies = [
        ('cs242_svn', '0002_auto_20141023_2334'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='svnfile',
            name='commit',
        ),
        migrations.AddField(
            model_name='project',
            name='revision',
            field=models.CharField(default=datetime.date(2014, 10, 24), max_length=10),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='svnfile',
            name='revision',
            field=models.CharField(default=datetime.date(2014, 10, 24), max_length=10),
            preserve_default=False,
        ),
        migrations.AlterField(
            model_name='project',
            name='date',
            field=models.CharField(max_length=50),
        ),
        migrations.AlterField(
            model_name='project',
            name='description',
            field=models.CharField(max_length=200),
        ),
        migrations.AlterField(
            model_name='svnfile',
            name='date',
            field=models.CharField(max_length=50),
        ),
        migrations.AlterField(
            model_name='svnfile',
            name='size',
            field=models.CharField(max_length=10),
        ),
    ]
