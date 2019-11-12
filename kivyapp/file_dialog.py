from kivy.uix.floatlayout import FloatLayout
from kivy.uix.popup import Popup


class FileDialog(FloatLayout):
    def __init__(self, on_file_selected, dialog_title, btn_text, **kwargs):
        """
        @param on_file_selected: a callback returning the selected file's name
        @param dialog_title: the text for the button in the dialog
        """
        super().__init__(**kwargs)

        self._on_file_selected = on_file_selected
        self._file_chooser = self.ids.file_chooser
        self._file_chooser.bind(selection=lambda obj, val: self._on_selection())

        self._btn_cancel = self.ids.btn_cancel
        self._btn_cancel.on_release = self._dismiss_popup

        self._btn_load = self.ids.btn_load
        self._btn_load.text = btn_text
        self._btn_load.on_release = self._btn_load_clicked

        self._text_input = self.ids.text_input

        self._popup = Popup(title=dialog_title, content=self, size_hint=(0.8, 0.8))

    def show(self):
        self._popup.open()

    def _dismiss_popup(self):
        self._popup.dismiss()

    def _btn_load_clicked(self):
        # TODO Check if image?
        path = self._file_chooser.path
        content = self._text_input.text
        filename = (self._file_chooser.selection and self._file_chooser.selection[0]) or \
                   ((len(content) > 0) and content) or ""
        self._on_file_selected(path, filename)
        self._dismiss_popup()

    def _on_selection(self):
        self._text_input.text = (
            self._file_chooser.selection and self._file_chooser.selection[0] or ""
        )
