<template>
  <div class="hello">
    <div class="dealer" >
      <img :src="img" alt="карта1">
      <img src="" alt="карта2">
    </div>
    <div class="mb-5">
      <img src="" alt="карта3">
      <img src="" alt="карта4">
    </div>
    <div>
      <button class="btn btn-info">More cards</button>
      <button class="btn btn-danger" name="Done">Done</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BlackJack',
  data(){
    return {
      img:"/backend/storage/files/6D.jpg",
      dealerCards:[],
      playerCards:[]
    }
  },
  props: {
    msg: String
  },
  methods: {

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
  .dealer{
    margin-bottom: 20rem;
  }
</style>
