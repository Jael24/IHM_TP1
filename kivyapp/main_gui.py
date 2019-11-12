import os
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.popup import Popup
from kivyapp.file_dialog import FileDialog


class MainGUI(FloatLayout):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)

        # Initialize class variables
        self.image_path = None
        self._popup = Popup()

        # Initialize components
        self._load_dialog = FileDialog(on_file_selected=self.load_file, dialog_title="Load file", btn_text="Load")
        self._save_dialog = FileDialog(on_file_selected=self.save_file, dialog_title="Save file", btn_text="Save")

        # Set up actions for buttons
        self._init_menu()

    def _init_menu(self):
        self._menu_load_image = self.ids.load_image
        self._menu_load_image.on_release = self._load_dialog.show

        self._menu_save_annotations = self.ids.save_annotations
        self._menu_save_annotations.on_release = self._save_dialog.show

        self._menu_save_image = self.ids.save_image
        self._menu_save_image.on_release = self._save_dialog.show  # TODO probably another callback

    def load_file(self, path, filename):
        # Load image from chooser
        self.image_path = os.path.join(path, filename)
        self.ids.image.source = self.image_path

    def save_file(self, path, filename):
        print(path, "||", filename)
        # Writes the content of the widget into a file
        # self.path_and_file_name = os.path.join(path, filename)
        # with open(self.path_and_file_name, "w") as stream:
        #     stream.write(self._textpad.text)
        # self.modified = False
