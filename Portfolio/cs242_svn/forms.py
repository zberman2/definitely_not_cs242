from django import forms
from cs242_svn.models import Message

__author__ = 'Zack Berman'


class MessageForm(forms.ModelForm):
    """
    template for a form to retrieve a message
    from the index.html page
    """
    class Meta:
        model = Message
        fields = ['text']