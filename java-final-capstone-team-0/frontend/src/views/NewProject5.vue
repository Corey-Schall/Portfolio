<template>
   <div>
        <h1 id="title">Step 5: Examine cost estimate</h1>
        <div id="flexbox-format-container">        
                <div id="blueprint-zone">
                    <div id="grid-target-container" :style="gridSize">
                        <div id="grid-target" class="dropzone" :style="gridLineStyling">
                            <div :id="room.roomName.substring(0,1)+room.roomId" v-for="room in displayedRooms" v-bind:key="room.roomId" class="rooom" :style='styleString(room)'>
                            <p>{{room.roomName}}</p>
                            <p>${{roomCost(room)[0].toFixed(2) }}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="side-menu">
            <h2 id="header-for-costs">Cost of room options</h2>
            <div id="floor-selector-buttons-container">
                <div id="flexbox" v-for="floor in $store.state.currentProject.floors" v-bind:key="floor.floorId">
                <button class="floor-selector" v-on:click="floorClick1(floor)">{{floor.floorName}}</button>
                    <p id="cost-per-floor">${{floorCost(floor)[0].toFixed(2) }}</p>
                </div>
            </div>
                <span id="spacer"></span>
                    <div id="total-cost">
                        <h2>Total Cost: ${{(houseCost() + plotCost).toFixed(2) }}</h2>
                    </div>
                </div>
        </div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            rooms: [],
            displayedRooms: [],
            floors: [],
            plotCost: 0
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
        pxToFt(){
          
          /*
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
          */
         return 20;
        }
    },
    methods: {
        roomCost(room){
          let cost = 0;
          room.fixtures.forEach((fixture)=>{
            this.$store.state.fixtures.forEach((fixtureType)=>{
                    if(fixture.fixtureType == fixtureType.fixtureType){
                        cost += fixtureType.economicCost;
                        console.log(`fixture cost: ${cost}`);
                    }
                });
          });
          let p = 2 * ((room.length / this.pxToFt) + (room.width / this.pxToFt));
          console.log(p);
          let a = (room.length / this.pxToFt) * (room.width / this.pxToFt);
          console.log(a);
          this.$store.state.wallTypes.forEach((type)=>{
                if(type.wallTypeName == room.wallTypeName){
                  cost += (type.wallCost * p) + 4000; //extra 4000 for electric/plumbing
                }
          });
          console.log(`cost after walls:${cost}`);
          this.$store.state.floorTypes.forEach((type)=>{
                if(type.floorTypeName == room.floorTypeName){
                  cost += (type.cost * a);
                }
          });
          console.log(`cost after floors: ${cost}`);
          return [cost, a];
        },
        floorCost(floor){
            let cost = 0;
            floor.rooms.forEach(item => {
                let array = this.roomCost(item);
                cost += array[0];
                console.log(`room costs: ${cost}`);
            });
            let floorArea = (this.$store.state.currentProject.foundationWidth * this.$store.state.currentProject.foundationLength)/(this.pxToFt);
            cost += floorArea * 40; //for ceiling stuff
            let perimeter = ((this.$store.state.currentProject.foundationWidth + this.$store.state.currentProject.foundationLength) * 2)/(this.pxToFt);
            cost += perimeter * 100; // for siding/insulation
            return [cost, floorArea];
        },
        houseCost(){
            let cost = 0;
            let area = 0;
            this.$store.state.currentProject.floors.forEach((floor)=>{
                cost += this.floorCost(floor)[0];
                area = this.floorCost(floor)[1];
                });
                cost += area * 180; 
                this.$store.state.regions.forEach((region) =>{
                    if(this.$store.state.currentProject.regionName == region.regionName){
                        cost *= region.regionCost;
                    }
                });
            return cost;
        },
        route(room){
            return `/${this.$route.params.projectId}/Step4/${room.roomId}`;
        },
        floorClick1(floor) {
            this.displayedRooms = [];
            floor.rooms.forEach((room)=>{
                if(!(room.xCoordinate<0||room.yCoordinate<0)){
                    this.displayedRooms.push(room);
                    this.setPosition(room);
                }
            });
        },
        styleString(room){
            let style = `top: ${room.y-16}px; left: ${room.x-16}px; height: ${room.length}px; width: ${room.width}px;`;
            console.log(style);
            return style;
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
    }
}
</script>

<style scoped>

* {
    font: Roboto,RobotoDraft,Arial,sans-serif;
}
 .rooom{
        width: 120px;
        padding: 20px;
        margin: 1rem;
        background-color: rgba(255, 255, 255, 0.233);
        color: white;
        font-size: 20px;
        font-family: sans-serif;
        overflow: hidden;
        touch-action: none;
        box-sizing: border-box;
        position: absolute;
        border-color: ivory;
        border-style: solid;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

body {
  background-color: #362c10;
}

#flexbox-format-container { 
  margin: 50px;
  margin-top: 30px;
  box-sizing: border-box;
  width: 90vw;
  height: 80vh;
  background-color: #a6c4ff;
  border: 5px solid;
  border-color: #32425f;
  border-radius: 5px;
  display: flex;
  padding: 30px;
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

#grid-target-container{
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
#header-for-costs{
    text-align: center;
}

#floor-selector-buttons-container {
 padding: 10px;
 width: 90%;
 display: flex;
 flex-direction: column;
 justify-content: center;
 align-content: center;
 
}

#flexbox{
    display: flex;
    align-self: center;
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

#spacer{
    flex-grow: 1;
}

#total-cost{
    text-align: center;
}

a:link, a:visited {
        text-decoration: none;
        color: inherit;
}

a:hover {
        color: #A6C4FF;
}

 #title {
        font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
        color: #32425F;
        text-align: center;
        margin-top: 100px;
    }

#land{
    margin-top: 10vh;
}
</style>