
<template>
    <div id="flexbox-format-container">
        <div v-if="$store.state.isLoading">
            <img src="../assets/TE_final_capstone_housebuilder_logo_sized_light_blue.png" class = 'rotate' width="200" height="200"/>
        </div>
        <h1  v-if="!$store.state.isLoading">Step 3: Choose the placement of the rooms on each floor:</h1>
        <div id="draggable-element-container" v-if="!$store.state.isLoading" >
            <div id="blueprint-zone">
                <div id="grid-target-container" :style="gridSize">
                    <div id="grid-target" class="dropzone" :style="gridLineStyling">
                        <div :id="room.roomName.substring(0,1)+room.roomId" v-for="room in displayedRooms" v-bind:key="room.roomId" class="in-zone" @dblclick="unspawnRoom(room)" :style='styleString(room)'><router-link :to="route(room)">{{room.roomName}}<br>{{ Math.round(room.width / pxToFeet)}}' x {{Math.round(room.length / pxToFeet)}}'</router-link></div>
                    </div>
                </div>
            </div>
            <div id="side-menu">
                <div id="floor-selector-buttons-container">
                    <button class="floor-selector" v-for="floor in $store.state.currentProject.floors" v-bind:key="floor.floorId" v-on:click="floorClick1(floor)">{{floor.floorName}}</button>
                </div>
                <div id="room-tray-container">
                    <div id="room-tray-text">Room Tray</div>
                    <div id="room-tray">
                        <button class="sample-room" v-for="room in rooms" v-bind:key="room.roomId" v-on:click="spawnRoom(room)">{{room.roomName}}</button>
                    </div>
                </div>
                <br>
                <br>
                <p>Adjust room size as needed.</p>
                <p>Double click room to remove from grid.</p>
                <p>Click on room name to arrange fixtures.</p>
                <br>
                <button id="save-button" v-on:click="saveToDatabase">Save Room Position</button>
                <button id="finish-button" v-on:click="pushStep5" >Calculate House Cost</button> 
            </div>
        </div>
    </div>
</template>

<script scoped>
import interact from 'interactjs';
import ProjectService from '../services/ProjectService';

export default {
    data() {
        return {
            rooms: [],
            displayedRooms: [],
            floorId: -1
        }
    },
    computed: {
        gridSize() {
            let styleString = 0;
            const projectRatio = this.$store.state.currentProject.foundationWidth / this.$store.state.currentProject.foundationLength;

            if (projectRatio > 1) {
                styleString = "position: relative; width: 100%; padding-top: " + ((100 / projectRatio) / 2) + "%; padding-bottom: " + ((100 / projectRatio) / 2) + "%;";
            }

            /*
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
            */
            
            else {
                styleString = "position: relative; height: 100%; padding-left: " + ((100 * projectRatio) / 2) + "%; padding-right: " + ((100 * projectRatio) / 2) + "%;";
            }

            return styleString;
        },
        gridLineStyling() {
            const horizontalSpacing = 100 / this.$store.state.currentProject.foundationWidth;
            console.log("horizontalSpacing: " + horizontalSpacing);
            console.log("this.$store.state.currentProject.foundationWidth: " + this.$store.state.currentProject.foundationWidth);
            const verticalSpacing = 100 / this.$store.state.currentProject.foundationLength;
            console.log("this.$store.state.currentProject.foundationLength: " + this.$store.state.currentProject.foundationLength);
            console.log("verticalSpacing: " + verticalSpacing);

            let styleString = "background-image: linear-gradient(#5C94FF, transparent 1px), linear-gradient(90deg, #5C94FF, transparent 1px); " + 
                                "background-size: " + horizontalSpacing + "% " + verticalSpacing + "%;"  + 
                                "display: flex; flex-direction: column;"
            
            console.log(styleString);
            return styleString;
        },
        pxToFeet(){
          
          const blueprintZone = document.getElementById('grid-target');
          const zoneSize = getSizeXY(blueprintZone);
          let zoneWidth = zoneSize[0];

          let projectWidth = this.$store.state.currentProject.foundationWidth;
          
          return zoneWidth/projectWidth;

            // gets size of element in frame
            function getSizeXY(element) {
                const rect = element.getBoundingClientRect();
                const elemWidth = rect.width;
                const elemHeight = rect.height;
                console.log("elemWidth: " + elemWidth + ", elemHeight: " +elemHeight);
                return [elemWidth, elemHeight];
            }

        }
    },
    methods: {
        route(room){
            return `/${this.$route.params.projectId}/Step4/${room.roomId}/${room.width / this.pxToFeet}`;
        },
        pushStep5(){
            this.$store.commit('SET_PROJECT_STEP', 5);
            ProjectService.updateProject(this.$store.state.currentProject).then();
            this.$router.push(`/${this.$route.params.projectId}/Step5/`);
        },

        saveToDatabase(){
            this.savePositions();
            this.rooms.forEach((room)=>{
                ProjectService.updateRoom(room).then();
            });

            this.displayedRooms.forEach((room)=>{
                ProjectService.updateRoom(room).then();
            });
        },
        floorClick1(floor) {
            if(floor.floorId != this.floorId){
                this.savePositions();
                this.rooms = [];
                this.displayedRooms = [];
                floor.rooms.forEach((room)=>{
                    if(room.xCoordinate<0||room.yCoordinate<0){
                        this.rooms.push(room);
                    }
                    else{
                        this.displayedRooms.push(room);
                        this.setPosition(room);
                    }
                });
                this.floorId = floor.floorId;
            }
        },
        styleString(room){
            let style = `top: ${room.y-1}px; left: ${room.x-1}px; height: ${room.length}px; width: ${room.width}px; position: absolute;`;
            console.log(style);
            return style;
        },
        spawnRoom(room) {
            this.displayedRooms.push(room);
            this.rooms = this.rooms.filter(item => {
                return room.roomId != item.roomId;
            })
        },
        unspawnRoom(room) {
            room.xCoordinate = -1;
            room.yCoordinate = -1;
            room.width = 200;
            room.length = 150;

            this.rooms.push(room);
            this.displayedRooms = this.displayedRooms.filter(item => {
                return room.roomId != item.roomId;
            })
        },

        // grabs position and size of blueprint zone, gets position of each element inside blueprint zone,
        // and logs their positions as a percentage of distance across the zone
        savePosition(roomElementId) {

            const blueprintZone = document.getElementById('grid-target');

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
        this.displayedRooms.forEach(room => {
            let roomElementId = room.roomName.substring(0,1)+room.roomId;
            let position = this.savePosition(roomElementId);
            room.xCoordinate = position[0];
            room.yCoordinate = position[1];
            room.width = position[2];
            room.length = position[3];
        });
        },
        setPosition(room) {

            //get width and height and coordinates of dropzone
            //get percentages from room
            //multiply x percentage from room by width of dropzone
            //same for y and height
            //add those numbers to coordinates of dropzone
            //set room elements coordinates, height, and width
            
            const blueprintZone = document.getElementById('grid-target');

            // declares dimension lets for blueprintZone
            let zoneWidth = 0;
            let zoneHeight = 0;

            // grabs dimensions using helper methods
            const zoneSize = getSizeXY(blueprintZone);
            zoneWidth = zoneSize[0];
            zoneHeight = zoneSize[1];

            const roomxPositionInZone = room.xCoordinate * zoneWidth;
            const roomyPositionInZone = room.yCoordinate * zoneHeight;

            // grabs room in the blueprint area
            room.x = roomxPositionInZone;
            room.y = roomyPositionInZone;


            // gets size of element in frame
            function getSizeXY(element) {
                const rect = element.getBoundingClientRect();
                const elemWidth = rect.width;
                const elemHeight = rect.height;
                console.log("elemWidth: " + elemWidth + ", elemHeight: " +elemHeight);
                return [elemWidth, elemHeight];
            }
        }
    },
    mounted() {
        const inZone = interact('.in-zone');
        const dropzone = interact('.dropzone');
        const position = {x: 0, y:0};

        /*
        function gridSnapX() {
            const horizontalSpacing = 100 / this.$store.state.currentProject.foundationWidth;
            const gridWidth = document.getElementById('grid-target').getBoundingClientRect().width;
            return horizontalSpacing * gridWidth;
        }

        function gridSnapY() {
            const verticalSpacing = 100 / this.$store.state.currentProject.foundationLength;
            const gridHeight = document.getElementById('grid-target').getBoundingClientRect().height;
            return verticalSpacing * gridHeight;
        }
        */

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

        inZone
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

            .resizable({
                preserveAspectRatio: false,
                
                edges: {
                    left: true,
                    right: true,
                    bottom: true,
                    top: true
                },
                // TODO: ADD SNAPPING
                /*
                modifiers: [
                    interact.modifiers.snap({
                        targets: [
                            interact.snappers.grid({
                            x: gridSnapX(),
                            y: gridSnapY(),
                            })
                        ]
                    })
                ]
                */
                
                
            })

                .on('dragstart', function (event) {
                    event.preventDefault();
                })
                .on('dragmove', dragMoveListener)
                .on('resizestart', function(event) {
                    console.info('resizestart = ', event);
                })
                .on('resizemove', function(event) {
                    console.info('resizemove = ', event);
                    var target = event.target,
                    x = (parseFloat(target.getAttribute('data-x')) || 0),
                    y = (parseFloat(target.getAttribute('data-y')) || 0);

                    // update the element's style
                    target.style.width = event.rect.width + 'px';
                    target.style.height = event.rect.height + 'px';

                    // translate when resizing from top or left edges
                    x += event.deltaRect.left;
                    y += event.deltaRect.top;

                    target.style.webkitTransform = target.style.transform =
                    'translate(' + x + 'px,' + y + 'px)';

                    target.setAttribute('data-x', x);
                    target.setAttribute('data-y', y);
            });

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
    },
    created(){
        let roomCounter = 0; 
        this.$store.state.currentProject.floors.forEach((floor)=>{
            roomCounter += floor.rooms.length;
        });
        if(roomCounter < this.$store.state.roomNumber){
            location.reload();
        }
    }
}


</script>


<style scoped>
    body {
        background-color: #F6F1E3;
    }

    .in-zone{
        width: 120px;
        padding: 20px;
        background-color: rgba(255, 255, 255, 0.233);
        color: white;
        text-align: center;
        text-decoration: none;
        font-size: 20px;
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
    }

    a:link, a:visited {
        text-decoration: none;
        color: inherit;
    }

    a:hover {
        color: #A6C4FF;
    }

    #flexbox-format-container {
        width: 100vw;
        height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
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
      
        border-radius: 5px;
        align-self: center;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    #grid-target-container {
        box-sizing: border-box;
    }

    #grid-target {
        position: absolute;
        top: 0;
        left: 0;
        border: 1px solid;
        border-color: #A6C4FF;
        height: 100%;
        width: 100%;
        
    }

    #side-menu {
        flex-grow: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        background-color:#F6F1E3;
        border: 2px solid;
        border-radius: 5px;
        padding: 15px;
        margin: 0px 0px 0px 30px;
    }

    #floor-selector-buttons-container {
        padding: 10px;
        width: 90%;
        display: flex;
        justify-content: center;
        align-content: center;

    }

    .floor-selector {
        background-color:#32425F;
        color:white;
        border-radius: 50%;
        border-top-style: hidden;
        border-right-style: hidden;
         border-left-style: hidden;
        border-bottom-style: hidden;
        height: 90%;
        width: auto;
        padding: 5px;
        margin: 15px;
        font-size: 1em;
    }

    #room-tray-container {
        width: 90%;
        height: 64%;
        align-self: center;
        display: flex;
        flex-direction: column;
        align-items: center;
        
        
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
        flex-grow: 1;
        flex-wrap: wrap;
        max-width: 45vh;
        overflow:scroll;
        
        
        
    }

    .sample-room {
        width: 30%;
        height: 15%;
        min-height: 25px;
        background-color: lightblue;
        color: rgb(34, 33, 33);
        text-align: center;
        margin: 10px;
        border-radius: 10px;
          border-top-style: hidden;
        border-right-style: hidden;
         border-left-style: hidden;
        border-bottom-style: hidden;
    }

    #save-button, #finish-button {
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

    #save-button:hover, #finish-button:hover{
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
        text-align: center;
        margin-top: auto;
    }
    p{
        margin: 0;
        padding: 0;
        text-align: center;
    }

</style>