<template>
  <form id="flexbox-format-container" @submit.prevent="nextClick">
    <div id="grid">
      <h1>Step 2:</h1>
      <h1 id="directions">Choose the number of rooms</h1>
      <br>
      <div id="bedAndBathInput">
        <input required min="1"
          type="number"
          placeholder="How many bedrooms?"
          v-model="info.num0fBedrooms"
          @change="bedroomChange"
        />
        <input required min="1"
          type="number"
          placeholder="How many bathrooms?"
          v-model="info.num0fBathrooms"
          @change="bathroomChange"
        />
        <input required min="1"
          type="number"
          placeholder="How many floors?"
          v-model="info.numOfFloors"
          @change="floorChange"
        />
      </div>

      <div id="essential-rooms">
        <div class="checkbox-span" v-for="room in info.possibleRooms" :key="room.roomName">
          <input type="checkbox" class="checkbox"  @change="toggleRoom(room)"/>
          <label>{{ room.roomName }} </label>
        </div>
      </div>
      <br>
      <h1 id="directions">Choose the floor of each room</h1>
      <div id="flex-structure">
        <div
          v-for="room in allRooms"
          v-bind:key="room.name"
          class="roomOptions"
        >
          <p>{{ room.roomName }}</p>
          <select name="Floor" v-model="room.floorId">
            
            <option required
              v-bind:key="floor.floorOrder"
              v-for="floor in info.floors"
              v-bind:value="floor.floorOrder"
            >
              {{ floor.floorName }}
            </option>
          </select>
        </div>
      </div>
      <button type="submit">Next</button>
    </div>
  </form>
</template>

<script>
import ProjectService from "../services/ProjectService";

export default {
  data() {
    return {
      info: {
        numOfFloors: null,
        num0fBedrooms: null,
        numOfBathroom: null,
        floors: [],
        bedrooms: [],
        bathrooms: [],
        additionalRooms: [],
        possibleRooms: [{roomName: 'Kitchen'}, {roomName:'Dining Room'} , {roomName: 'Office'} ,{roomName: 'Living Room'} ],
        checked: false,
      },
    };
  },
  computed: {
    allRooms() {
      return this.info.bedrooms.concat(this.info.bathrooms).concat(this.info.additionalRooms);
    },
  },
  methods: {
    floorChange() {
      let arr = [];
      for (let i = 1; i <= this.info.numOfFloors; i++) {
        let obj = {
          projectId: this.$store.state.currentProject.projectId,
          floorName: "Floor " + i,
          floorOrder: i,
        };
        arr.push(obj);
      }
      this.info.floors = arr;
    },

    bedroomChange() {
      let arr = [];
      for (let i = 1; i <= this.info.num0fBedrooms; i++) {
        let obj = {
          floorId: null,
          roomName: "Bedroom " + i,
        };
        arr.push(obj);
      }
      this.info.bedrooms = arr;
    },

    bathroomChange() {
      let arr = [];
      for (let i = 1; i <= this.info.num0fBathrooms; i++) {
        let obj = {
          floorId: null,
          roomName: "Bathroom " + i,
        };
        arr.push(obj);
      }
      this.info.bathrooms = arr;
    },
        nextClick(){
            let roomCounter = 0;
            this.$store.commit('FLIP_ISLOADING');
            this.$store.commit('SET_PROJECT_STEP', 3);

            ProjectService.updateProject(this.$store.state.currentProject).then();

            this.info.floors.forEach((floor, index)=>{
                ProjectService.addFloor(floor).then((response)=>{
                    this.allRooms.forEach((room)=>{
                        roomCounter++;
                        if(response.data.floorOrder == room.floorId){
                            room.floorId = response.data.floorId;
                            ProjectService.addRoom(room).then();
                        }
                        
                    });
                    if(index == this.info.floors.length - 1){
                        ProjectService.getProjectDeets(this.$store.state.currentProject.projectId).then((response)=>{
                         this.$store.commit('SET_CURRENT_PROJECT', response.data);
                         this.$store.commit('FLIP_ISLOADING');
                         this.$store.commit('SET_ROOM_NUMBER', roomCounter);
                         });
                    }
                })
                
            });
            
            this.$router.push(`/${this.$store.state.currentProject.projectId}/Step3`);
    },
    toggleRoom(room){
        let arr = this.info.additionalRooms.filter((movedRoom) =>{
            return movedRoom.roomName != room.roomName;
        })
        
        if(arr.length == this.info.additionalRooms.length){
            this.info.additionalRooms.push(room);
        }else{
            this.info.additionalRooms = arr;
        }
    }
  },
};
</script>

<style scoped>

body {
  background-color: #f6f1e3;
}

.moveme {
  width: 120px;
  padding: 20px;
  margin: 1rem;
  background-color: rgba(255, 255, 255, 0.233);
  color: white;
  font-size: 20px;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
  overflow: hidden;
  touch-action: none;
  box-sizing: border-box;
  position: absolute;
  /* border-color: ivory; */
  border-style: solid;
}

#flexbox-format-container {
  display: flex;
  flex-direction: column;
  align-content: center;
  justify-content: center;
}

#grid {
  width: 70vw;
  height: 70vh;
  background-color: #3563d9;
  background-image: linear-gradient(#5c94ff, transparent 0.1em),
    linear-gradient(90deg, #5c94ff, transparent 0.1em);
  background-size: 2em 2em;

  padding: 30px;
  margin: auto;
  border-radius: 15px;
  position: absolute;
  top: 55%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
}

#foundationInput,
#bedAndBathInput,
#floorInput {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

#grid-target {
  width: 70%;
  height: 85%;
  background-color: blue;
  margin: 30px;
}
input,
h2,
select {
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
}

h2 {
  padding-right: 50px;
  padding-left: 50px;
}
::placeholder {
  color: #3563d9;
}
button {
  background-color: #a6c4ff;
  border-radius: 15px;
  border-top-style: hidden;
  border-right-style: hidden;
  border-left-style: hidden;
  border-bottom-style: hidden;
  padding: 10px;
  color: #3563d9;
  font-size: 1.3em;
}

h1 {
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
  color: #a6c4ff;
  text-shadow: 3px 3px 3px navy;
  text-align: center;
}

p {
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
  color: #a6c4ff;
  text-shadow: 1px 1px 1px navy;
  font-size: 20px;
}

.roomOptions {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

#flex-structure {
  height: 40%;
  display: flex;
  flex-flow: column wrap;
  overflow: scroll;
  
}

#essential-rooms {
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
  font-size: 16pt;
  color: #a6c4ff;
  text-shadow: 3px 3px 3px navy;
  text-align: center;
  display: flex;
  justify-content: center;
}
.checkbox-span {
  display: flex;
}

.checkbox {
  appearance: none;
  background-color: #3563d9;
  border: 1px solid rgb(255, 255, 255);
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05),
    inset 0px -15px 10px -12px rgba(0, 0, 0, 0.05);
  padding: 9px;
  border-radius: 3px;
  display: inline-block;
  position: relative;
}

.checkbox:active,
.checkbox:checked:active {
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05),
    inset 0px 1px 3px rgba(0, 0, 0, 0.1);
}

.checkbox:checked {
  background-color: #e9ecee;
  border: 1px solid #adb8c0;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05),
    inset 0px -15px 10px -12px rgba(0, 0, 0, 0.05),
    inset 15px 10px -12px rgba(255, 255, 255, 0.1);
  color: #99a1a7;
}
#directions {
  color: lightgray;
  font-size: 1.8em;
  
}
</style>