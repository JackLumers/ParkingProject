

var myMap; // Объект карты
var pointsCoordArray = []; // Координаты точек
var pointsCount = 0; // Сколько точек проставлено
var polygonCount = 0; // Сколько мест проставлено
var pointsObjects = []; // Объекты точек
var polygonObjects = []; // Объекты парковочных мест

// Функция ymaps.ready() будет вызвана, когда
// загрузятся все компоненты API, а также когда будет готово DOM-дерево.
ymaps.ready(init);

/** Инициализация карты **/
function init() {
    // Создание карты
    myMap = new ymaps.Map("map", {
        // Координаты центра карты
        center: [55.785921, 49.123397],
        // Уровень масштабирования
        zoom: 19,
        // Для отображения на весь экран
        controls: ['fullscreenControl']
    });

    //Изменение опций карты
    myMap.options.set({
        yandexMapDisablePoiInteractivity: true,
        suppressObsoleteBrowserNotifier: true,
        suppressMapOpenBlock: true,
        restrictMapArea: [[55.787144, 49.120532], [55.784921, 49.124577]],
        exitFullscreenByEsc: false,
        minZoom: 18
    });

    //Вывод на полный экран
    var fullscreenControl = myMap.controls.get(0);
    fullscreenControl.enterFullscreen();
    myMap.controls.remove('fullscreenControl');

    //Установка слушателя события кликов
    myMap.events.add('click', function(event){
        addingParkingPlace(event);
    });

    //Установка кнопки редактирования
    var editButton = new ymaps.control.Button('<b>Редактировать</b>');
    editButton.events.add('press', function(){
        changeEditing();
    });
    myMap.controls.add(editButton, {
        float: "left",
        maxWidth: 150
    });
}

/** Включение/отключение функции редактирования **/
var isInEditMode = false; // В режиме редактирования ли
function changeEditing() {
    if(isInEditMode){
        isInEditMode = false;
        clearPoints();
    }
    else {
        isInEditMode = true;
    }
}

/** Создание парковочного места **/
function addingParkingPlace(event){
    if(isInEditMode){
        var coords = event.get('coords');
        pointsCoordArray[pointsCount] = coords;
        addPoint(coords);
        alert(coords.join(', '));
        pointsCount++;
    }
    //Если проставили 4, то собирать полигон и обнулить кол-во точек
    if (pointsCount == 4) {
        addPolygon( pointsCoordArray[0],
                    pointsCoordArray[1],
                    pointsCoordArray[2],
                    pointsCoordArray[3]
        );
        clearPoints();
    }
}
function addPoint(coords){
    pointsObjects[pointsCount] = new ymaps.GeoObject({
        geometry: {
            type: "Circle",
            coordinates: coords,
            radius: 0.2
        }
    });
    myMap.geoObjects.add(pointsObjects[pointsCount]);
}
function addPolygon(x1, y1, x2, y2){
    polygonObjects[polygonCount] = new ymaps.GeoObject( {
            geometry: {
                type: "Polygon",
                coordinates: [[x1, y1, x2, y2]],
            },
            properties: {
                balloonContentHeader: 'Место № ' + (polygonCount+1),
                balloonContentBody: "Номер машины:</br>Занято до:</br>Владелец:</br>Мобильный:"
            }
        },
        {
            draggable: true
        }
    );
    polygonObjects[polygonCount].events.add(['balloonopen', 'balloonclose', 'press', ], function(event){
        alert("dddd");
    });
    myMap.geoObjects.add(polygonObjects[polygonCount]);
    polygonCount++;
}
/** ------------------------------------------ **/

/** Очистка переменных и массивов точек **/
function clearPoints(){
    pointsCoordArray = [];
    pointsCount = 0;
    for(var i = 0; i<pointsObjects.length; i++){
        myMap.geoObjects.remove(pointsObjects[i]);
    }
}
