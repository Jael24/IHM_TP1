from kivy.app import App
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.popup import Popup


class GUI(FloatLayout):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)

        # Initialize class variables
        self.modified = False
        self.path_and_file_name = None
        self._popup = Popup()

        # Retrieve your widgets
        self._init_menu()

    def _init_menu(self):
        self._menu_load_image = self.ids.load_image
        # self._menu_save.on_release = self.show_save_file

        self._menu_save_annotations = self.ids.save_annotations
        # self._menu_save_as.on_release = self.show_save_file_as

        self._menu_save_image = self.ids.save_image
        # self._menu_exit.on_release = self.show_exit


class Annotator(App):
    def build(self):
        return GUI()


if __name__ == "__main__":
    Annotator().run()
