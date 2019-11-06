import os

from kivy.app import App
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.popup import Popup
from kivyapp.dialogs import LoadDialog


class GUI(FloatLayout):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)

        # Initialize class variables
        self.modified = False
        self.path_and_file_name = None
        self._popup = Popup()

        # Initialize components
        self._load_dialog = LoadDialog(on_file_selected=self.load_file)

        # Set up actions for buttons
        self._init_menu()

    def _init_menu(self):
        self._menu_load_image = self.ids.load_image
        self._menu_load_image.on_release = self._load_dialog.show

        self._menu_save_annotations = self.ids.save_annotations
        # self._menu_save_annotations.on_release = self.e

        self._menu_save_image = self.ids.save_image
        # self._menu_save_image.on_release = self.func

    def load_file(self, path, filename):
        # TODO
        # Writes the content of the widget into a file
        self.path_and_file_name = os.path.join(path, filename)
        with open(self.path_and_file_name, "w") as stream:
            stream.write(self._textpad.text)
        self.modified = False


class Annotator(App):
    def build(self):
        return GUI()


if __name__ == "__main__":
    Annotator().run()
