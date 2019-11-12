from kivy.lang.builder import Builder
from kivy.uix.behaviors.drag import DragBehavior
from kivy.uix.label import Label
from kivy.app import App

kv = '''
<DragLabel>:
    # Define the properties for the DragLabel
    drag_rectangle: self.x, self.y, self.width, self.height
    drag_timeout: 10000000
    drag_distance: 0

FloatLayout:
    # Define the root widget
    DragLabel:
        size_hint: 1.0, 0.2
        text: 'Drag me'
        canvas.before:
            Color:
                rgb: .6, .6, .6
            Rectangle:
                pos: self.pos
                size: self.size 
'''


class DragLabel(DragBehavior, Label):
    pass


class RectangleApp(App):
    def build(self):
        obj = Builder.load_string(kv)
        return obj


if __name__ == '__main__':
    RectangleApp().run()
