from kivy.app import App
from kivyapp.main_gui import MainGUI


class Annotator(App):
    def build(self):
        return MainGUI()


if __name__ == "__main__":
    Annotator().run()
