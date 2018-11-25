
var myMap; // Объект карты
var pointsCoordArray = []; // Координаты точек
var pointsCount = 0; // Сколько точек проставлено
var polygonCount = 0; // Сколько мест проставлено
var pointsObjects = []; // Объекты точек
var polygonObjects = []; // Объекты парковочных мест
var isInEditMode = false; // В режиме редактирования ли

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
    //Если проставили 2, то собирать полигон и обнулить кол-во точек
    if (pointsCount == 2) {
        addPolygon( pointsCoordArray[0],
                    pointsCoordArray[1]
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
//x1, y1, x2, y2
function addPolygon(A, C){
    var B = [A[0], C[1]];
    var D = [C[0], A[1]];
    polygonObjects[polygonCount] = new ymaps.GeoObject( {
            geometry: {
                type: "Polygon",
                coordinates: [[A, B, C, D]],
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
        placeRotationMode();
        alert("rotationMode!");
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

/** Режим поворота места **/
function placeRotationMode(){
        //Установка кнопки редактирования
        var leftButton = new ymaps.control.Button('<b>d</b>');
        var rightButton = new ymaps.control.Button('<b>g</b>');
        leftButton.events.add('press', function(){
            rotatePlace(0);
        });
        rightButton.events.add('press', function(){
            rotatePlace(1);
        });
        myMap.controls.add(editButton, {
            float: "left",
            maxWidth: 150
        });
        myMap.controls.add(editButton, {
            float: "left",
            maxWidth: 150
        });
}

 function rotatePlace(side){
    alert("rotate!");
 }
