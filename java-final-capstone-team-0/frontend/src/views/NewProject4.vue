<template>

    <div id="flexbox-format-container">
        <div id="top">
        <button id = "back-button" @click="back">Back</button>
        <h1>Step 4: Choose the placement of the fixtures in {{thisRoom.roomName}}:</h1>
        </div>
        <div id="draggable-element-container">
            <div id="blueprint-zone">
                <div id="grid-target-container">
                        <div :id="fixture.fixtureType.substring(0,1)+fixture.id" v-for="fixture in displayedFixtures" v-bind:key="fixture.id" class="fixture" @dblclick="unspawnFixture(fixture)" :style='styleString(fixture)'>{{fixture.fixtureType}}</div>
                </div>
            </div>
            <div id="side-menu">
                <div id="room-tray-text">Choose floor and wall type:</div>
                <div id="floor-selector-buttons-container">
                    
                    <br>
                    <select name="floorTypes" v-model="thisRoom.floorTypeName">
                        <option v-bind:key = 'floorType.floorTypeName' v-for="floorType in $store.state.floorTypes" v-bind:value= 'floorType.floorTypeName'>{{floorType.floorTypeName}}</option>
                    </select>
                    <select name="wallTypes" v-model="thisRoom.wallTypeName">
                        <option v-bind:key = 'wallType.wallTypeName' v-for="wallType in $store.state.wallTypes" v-bind:value= 'wallType.wallTypeName'>{{wallType.wallTypeName}}</option>
                    </select>
                </div>
                <div id="room-tray-container">
                    <div id="room-tray-text">Click on fixture to add to room:</div>
                    <div id="room-tray">
                        <button class="fixtureButton" v-for="fixture in $store.state.fixtures" v-bind:key="fixture.fixtureName" v-on:click="spawnFixture(fixture)">Add {{fixture.fixtureType}}</button>
                    </div>
                </div>
                <br>
                <button id="save-button" v-on:click="saveRoom">Save Fixture Position</button>
            </div>
        </div>
    </div>
</template>

<script scoped>
import interact from 'interactjs';
import RoomService from '../services/RoomService';
import ProjectService from '../services/ProjectService';

export default {
    data() {
        return {
            displayedFixtures: [],
            id: 0,
            thisRoom: {},
            roomChange: null
        }
    },
    computed: {
        roomWidth(){
            const blueprintZone = document.getElementById('blueprint-zone');
            const width = blueprintZone.getBoundingClientRect().width;
            return width - this.roomChange;
        },
        pxToFt(){
            /*
          const blueprintZone = document.getElementById('grid-target-container');
          const zoneSize = getSizeXY(blueprintZone);
          let zoneWidth = zoneSize[0];

          let roomWidth = this.$route.params.roomWidthFt;
          console.log(`Room width px: ${zoneWidth}`);
          return zoneWidth/roomWidth;

            // gets size of element in frame
            function getSizeXY(element) {
                const rect = element.getBoundingClientRect();
                const elemWidth = rect.width;
                const elemHeight = rect.height;
                return [elemWidth, elemHeight];
            }
            */
           return 50;
        },
        gridLineStyling() {
            const horizontalSpacing = 100 / this.thisRoom.width;
            console.log("horizontalSpacing: " + horizontalSpacing);
            console.log("this.thisRoom.width: " + this.thisRoom.width);
            const verticalSpacing = 100 / this.thisRoom.length;
            console.log("this.thisRoom.length: " + this.thisRoom.length);
            console.log("verticalSpacing: " + verticalSpacing);

            let styleString = "background-image: linear-gradient(#5C94FF, transparent 1px), linear-gradient(90deg, #5C94FF, transparent 1px); " + 
                                "background-size: " + horizontalSpacing + "% " + verticalSpacing + "%;"  + 
                                "display: flex; flex-direction: column;"
            
            return styleString;
        }
    },
    methods: {
        gridSize() {
            /*
            let styleString = 0;
            const roomRatio = this.thisRoom.width / this.thisRoom.length;

            if (roomRatio > 1) {
                styleString = "position: relative; width: 100%; padding-top: " + ((100 / roomRatio) / 2) + "%; padding-bottom: " + ((100 / roomRatio) / 2) + "%;";
                this.roomChange = 0;
            }

            TODO: account for square houses
            else if (projectRatio == 1) {
                let blueprintZoneWidth = document.getElementById('blueprint-zone').getBoundingClientRect().width;
                let blueprintZoneHeight = document.getElementById('blueprint-zone').getBoundingClientRect().height;
                let blueprintZoneRatio = blueprintZoneWidth / blueprintZoneHeight;
                if (blueprintZoneRatio > 1) {
                    styleString = "position: relative; width: 100%; padding-top: " + ((100 / projectRatio) / 2) + "%; padding-bottom: " + ((100 / projectRatio) / 2) + "%;";
                }
                else {
                    styleString = "position: relative; height: 100%; padding-left: " + ((100 * projectRatio) / 2) + "%; padding-right: " + ((100 * projectRatio) / 2) + "%;";
                }
            }
            
            else {
                styleString = "position: relative; height: 100%; padding-left: " + ((100 * roomRatio) / 2) + "%; padding-right: " + ((100 * roomRatio) / 2) + "%;";
                this.roomChange = 2 * ((100 * roomRatio) / 2);
            }

            return styleString;
            */
        },
        saveRoom(){
            ProjectService.updateRoom(this.thisRoom).then();
            this.saveToDatabase();
        },
        back(){
            this.$router.push(`/${this.$route.params.projectId}/Step3`);
        },

        saveToDatabase(){
                    this.savePositions();
                    this.displayedFixtures.forEach((fixture)=>{
                        fixture.roomId = this.thisRoom.roomId;
                        RoomService.saveFixturePosition(fixture).then();
                    });
        },
        styleString(fixture){
            let style = `top: ${fixture.y}px; left: ${fixture.x}px; height: ${fixture.length*this.pxToFt}px; width: ${fixture.width*this.pxToFt}px;`;
            console.log(this.pxToFt);
            return style;
        },
        spawnFixture(fixture){
            let newFixture = {};
            newFixture = Object.assign(newFixture, fixture);
            fixture.roomId = this.thisRoom.roomId;
            RoomService.addFixtures(fixture).then((response) => {newFixture.fixtureId = response.data.fixtureId});
            newFixture.id = this.id;
            this.id ++;
            this.displayedFixtures.push(newFixture);
        },
        unspawnFixture(fixture) {
            this.displayedFixtures = this.displayedFixtures.filter(item => {
                return fixture.id != item.id;
            })
            RoomService.deleteFixture(fixture.fixtureId).then();
        },
        // grabs position and size of blueprint zone, gets position of each element inside blueprint zone,
        // and logs their positions as a percentage of distance across the zone
        savePosition(roomElementId) {

            const blueprintZone = document.getElementById('grid-target-container');

            // declares dimension lets for blueprintZone
            let zoneStartPointX = 0;
            let zoneStartPointY = 0;
            let zoneWidth = 0;
            let zoneHeight = 0;

            // grabs dimensions using helper methods
            const startPoints = getPositionXY(blueprintZone);
            zoneStartPointX = startPoints[0];
            zoneStartPointY = startPoints[1];
            const zoneSize = getSizeXY(blueprintZone);
            zoneWidth = zoneSize[0];
            zoneHeight = zoneSize[1];

            // grabs room in the blueprint area
            const roomInZone = document.getElementById(roomElementId);
            console.log(roomInZone);

            // add position for all rooms in the drop zone
            // run roomRelativeXY on each room in list
            // save xy percentage to db
            return roomRelativeXY(roomInZone).concat(getSizeXY(roomInZone));

            // gets the position of an element in the frame
            function getPositionXY(element) {
                let rect = element.getBoundingClientRect();
                let relativePosX = rect.x;
                let relativePosY = rect.y;
                console.log("relativePosX: " + relativePosX + ", relativePosY: " + relativePosY);
                return [relativePosX, relativePosY];
            }

            // gets size of element in frame
            function getSizeXY(element) {
                const rect = element.getBoundingClientRect();
                const elemWidth = rect.width;
                const elemHeight = rect.height;
                console.log("elemWidth: " + elemWidth + ", elemHeight: " +elemHeight);
                return [elemWidth, elemHeight];
            }

            // define function to grab relative position of room and save it to db
            function roomRelativeXY(element) {
                let roomStartPointX = 0;
                let roomStartPointY = 0;

                // get element xy position
                const roomStarts = getPositionXY(element);
                roomStartPointX = roomStarts[0];
                roomStartPointY = roomStarts[1];
                console.log("roomStartPointX: " + roomStartPointX + ", roomStartPointY: " + roomStartPointY);


                // get relative position of room in blueprint zone
                const roomRelativeX = roomStartPointX - zoneStartPointX;
                const roomRelativeY = roomStartPointY - zoneStartPointY;
                console.log("roomRelativeX: " + roomRelativeX + ", roomRelativeY: " + roomRelativeY);

                // get percentage of xy distance across zone
                const roomStartPercentX = roomRelativeX / zoneWidth;
                const roomStartPercentY = roomRelativeY / zoneHeight;
                return [roomStartPercentX, roomStartPercentY];
            }

        },
        savePositions(){
        this.displayedFixtures.forEach(fixture => {
            let fixtureElementId = fixture.fixtureType.substring(0,1)+fixture.id;
            let position = this.savePosition(fixtureElementId);
            fixture.xCoordinate = position[0];
            fixture.yCoordinate = position[1];
        });
        },
        setPosition(room) {

            //get width and height and coordinates of dropzone
            //get percentages from room
            //multiply x percentage from room by width of dropzone
            //same for y and height
            //add those numbers to coordinates of dropzone
            //set room elements coordinates, height, and width
            
            const blueprintZone = document.getElementById('grid-target-container');

            // declares dimension lets for blueprintZone
            let zoneWidth = 0;
            let zoneHeight = 0;

            // grabs dimensions using helper methods
            const zoneSize = getSizeXY(blueprintZone);
            zoneWidth = zoneSize[0];
            zoneHeight = zoneSize[1];


            // grabs dimensions using helper methods
            const startPoints = getPositionXY(blueprintZone);
            let zoneStartPointX = startPoints[0];
            let zoneStartPointY = startPoints[1];

            const roomxPositionInZone = room.xCoordinate * zoneWidth;
            const roomyPositionInZone = room.yCoordinate * zoneHeight;

            // grabs room in the blueprint area
            room.x = roomxPositionInZone + zoneStartPointX;
            room.y = roomyPositionInZone + zoneStartPointY;


            // gets size of element in frame
            function getSizeXY(element) {
                const rect = element.getBoundingClientRect();
                const elemWidth = rect.width;
                const elemHeight = rect.height;
                console.log("elemWidth: " + elemWidth + ", elemHeight: " +elemHeight);
                return [elemWidth, elemHeight];
            }

            // gets the position of an element in the frame
            function getPositionXY(element) {
                let rect = element.getBoundingClientRect();
                let relativePosX = rect.x;
                let relativePosY = rect.y;
                console.log("relativePosX: " + relativePosX + ", relativePosY: " + relativePosY);
                return [relativePosX, relativePosY];
            }
        }
    },
    created: function() {

        ProjectService.getProjectDeets(this.$route.params.projectId).then((response)=>{
            this.$store.commit('SET_CURRENT_PROJECT', response.data);
            let rooom;
            this.$store.state.currentProject.floors.forEach((floor)=>{
                floor.rooms.forEach((room)=>{
                    if(room.roomId == this.$route.params.roomId){
                        rooom = room;
                    }
                })
            })
            this.thisRoom = rooom;
            this.displayedFixtures = this.thisRoom.fixtures;
            this.displayedFixtures.forEach((fixture)=>{
                this.$store.state.fixtures.forEach((fixtureType)=>{
                    if(fixture.fixtureType == fixtureType.fixtureType){
                        fixture.length = fixtureType.length;
                        fixture.width = fixtureType.width;
                    }
                });
                fixture.id = this.id;
                this.id ++;
                this.setPosition(fixture);
            });
        });
    },
    mounted() {
        const fixture = interact('.fixture');
        const dropzone = interact('.dropzone');
        const position = {x: 0, y:0};

        function dragMoveListener(event) {
        var target = event.target,
            // keep the dragged position in the data-x/data-y attributes
            x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx,
            y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

        // translate the element
        target.style.webkitTransform =
            target.style.transform =
            'translate(' + x + 'px, ' + y + 'px)';

        // update the posiion attributes
        target.setAttribute('data-x', x);
        target.setAttribute('data-y', y);
        }

        window.dragMoveListener = dragMoveListener;

        fixture
            .draggable({
                inertia: true,

                // keep the element within the area of its parent

                modifiers: [

                interact.modifiers.restrictRect({

                    restriction: 'parent',

                    endOnly: true

                })
                ],

                listeners: {
                    start (event) {
                        console.log(event.type, event.target)
                    },

                    move (event) {
                        position.x += event.dx
                        position.y += event.dy

                        event.target.style.transform =
                        `translate(${position.x}px, ${position.y}px)`
            }}})
            .on('dragstart', function (event) {
                    event.preventDefault();
                })
            .on('dragmove', dragMoveListener)

        dropzone
            .dropzone({
            // only accept elements matching this CSS selector
            accept: '.in-zone',
            // Require a 100% element overlap for a drop to be possible
            overlap: 1.00,

            // listen for drop related events:

            ondropactivate: function (event) {
                // add active dropzone feedback
                event.target.classList.add('drop-active')
            },
            ondragenter: function (event) {
                var draggableElement = event.relatedTarget
                var dropzoneElement = event.target

                // feedback the possibility of a drop
                dropzoneElement.classList.add('drop-target')
                draggableElement.classList.add('can-drop')
            },
            ondragleave: function (event) {
                // remove the drop feedback style
                event.target.classList.remove('drop-target')
                event.relatedTarget.classList.remove('can-drop')
            },
            /*
            ondrop: function (event) {
                event.relatedTarget.textContent = 'Dropped'
            },
            */
            ondropdeactivate: function (event) {
                // remove active dropzone feedback
                event.target.classList.remove('drop-active')
                event.target.classList.remove('drop-target')
            }
        })
    }
}


</script>


<style scoped>
    body {
        background-color: #F6F1E3;
    }

    .fixture{
        width: 120px;
        padding: 20px;
        background-color: rgba(255, 255, 255, 0.233);
        color: white;
        font-size: 8px;
        font-family: sans-serif;
        overflow: hidden;
        touch-action: none;
        box-sizing: border-box;
        position: absolute;
        border-color: ivory;
        border-style: solid;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 10px;
        min-width: 10px;
        
    }

    #flexbox-format-container {
        width: 100vw;
        height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        position: absolute;
        top: 70px;
    }

    #draggable-element-container {
        box-sizing: border-box;
        width: 90vw;
        height: 80vh;
        background-color:#A6C4FF;
        border: 5px solid;
        border-color: #32425F;
        border-radius: 5px;
        display: flex;
        padding: 30px;
        
    }

    #blueprint-zone {
        box-sizing: border-box;
        flex-basis: 70%;
        height: 100%;
        background-color: #3563D9;
        border: 2px solid;
        border-color: #32425F;
        border-radius: 5px;
        align-self: center;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    #grid-target-container {
        top: 0px;
        right: 0px;
        bottom: 0px;
        left: 0px;
        width: 100%;
        height: 100%;
        background-image: linear-gradient(#5C94FF, transparent .1em), linear-gradient(90deg, #5C94FF, transparent .1em);
        background-size: 1em 1em;
    }

    #side-menu {
        flex-grow: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        background-color:#F6F1E3;
        border: 2px solid;
        border-color: #32425F;
        border-radius: 5px;
        padding: 15px;
        margin: 0px 0px 0px 30px;
    }
    select {
        font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
        color: #3563D9;
        margin-left: 20px;
        margin-bottom: 20px;
        padding: 10px;
        background-color: #A6c4ff;
        border-radius: 15px;
        border-top-style: hidden;
        border-right-style: hidden;
        border-left-style: hidden;
        border-bottom-style: hidden;
        font-size: 1em;
    }
    #floor-selector-buttons-container {
        padding: 10px;
        width: 90%;
        display: flex;
        justify-content: center;
        align-content: center;
        
    }

    .floor-selector {
        background-color:#F6F1E3;
        color:#32425F;
        border-radius: 50%;
        height: 90%;
        width: auto;
        padding: 5px;
        margin: 15px;
    }

    #room-tray-container {
        width: 90%;
        height: 64%;
        align-self: center;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    #room-tray-container::-webkit-scrollbar {
        width: 2px;
    }

    #room-tray-text {
        color:#32425F;
        font-weight: bold;
        text-align: center;
        margin: 20px;
        font-size: 1.2em;
    }

    #room-tray {
        width: 100%;
        height: 90%;
        background-color: #D2D2D2;
        border-radius: 10px;
        display: flex;
        flex-wrap: wrap;
        max-width: 45vh;
        overflow:scroll;
        justify-content: center;
    }

    .fixtureButton {
        width: 40%;
        height: 25%;
        min-height: 45px;
        background-color: lightblue;
        color: #32425F;
        text-align: center;
        margin: 10px;
        border-radius: 10px;
        border-top-style: hidden;
        border-right-style: hidden;
        border-left-style: hidden;
        border-bottom-style: hidden;
        
    }

    #save-button {
        margin-left: 20px;
        margin-bottom: 20px;
        padding: 10px;
        background-color: #a6c4ff;
        border-radius: 15px;
        border-top-style: hidden;
        border-right-style: hidden;
         border-left-style: hidden;
        border-bottom-style: hidden;
        font-size: 1em;
        width: 60%;
        font-size: 1.2em;
        box-shadow: 5px 5px 5px grey;
    }

    #save-button:hover{
    background-color: #b4cdff;
    transition: all .5s;
    transform : translateY(-10px);
    box-shadow: 5px 10px 10px grey;
    }
    

    #back-button{
        width: 5%;
        min-height: 25px;
        background-color:#a6c4ff;
        color:#32425F;
        text-align: center;
        margin: 30px;
        align-self: flex-start;
        order: 2;
        padding: 10px;
        background-color: #a6c4ff;
        border-radius: 15px;
        border-top-style: hidden;
        border-right-style: hidden;
         border-left-style: hidden;
        border-bottom-style: hidden;
        box-shadow: 5px 5px 5px grey;
       
        font-size: 1.2em;
    }

    #back-button:hover{
    background-color: #b4cdff;
    transition: all .5s;
    transform : translateY(-10px);
    box-shadow: 5px 10px 10px grey;
    }

    .rotate{
        animation: rotation 2s infinite linear;
    }

    @keyframes rotation{
        from {
            transform: rotate(0deg);
        }
        to{
            transform: rotate(359deg);
        }
    }

    h1 {
        font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
        color: #32425F;
        order: 3;
        flex-grow: 2;
        padding-top: 1%;
        
        
    }
    #top {
        display: flex;
        flex-direction: row;
        width: 93%;
    }

    

</style>