Здесь я буду писать какие решения при написании программы я принимал и почему:

1. Разделил программу на 4 части: игровое поле, игрок, интерфейс для пользователя и сама игра
2. Я решил сделать фишку с помощью 2 элементов: enum Color и class Chip, чтобы у самой фишки можно было вызвать метод changeColor(если бы не было класса, а только перечисление, то приходилось бы постоянно смотреть на цвет и в зависимости от него переприсваивать новый цвет)
3. Создал свой тип исключения CellOccupiedException, который вылетает когда мы хотим занять клетку где уже есть фишка. Нужно, чтобы в будущем при обработке отличить его от других
4. Из-за того, что есть цепочка вложенности(Board хранит экземпляр BoardData, который хранит массив Chip-ов, которые хранят в себе Color), есть проблема с распределением ответственности. Например, непонятно должен ли класс BoardData при добавлении фишки на поле учитывать правила игры и если да, то какие. Или непонятно, пустая клетка должна хранить просто null или же Chip с color = null
