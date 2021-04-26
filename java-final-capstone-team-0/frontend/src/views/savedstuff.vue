
<template>
    <div>
        <div class='resizable'>This is a box
            </div>
        <button id=save>Save</button>
    </div>
</template>

<script>
import interact from 'interactjs';

const moveme = interact('.moveme');
const position = {x: 0, y:0};

moveme
  .draggable({ 
    inertia: true,
    listeners: {
    start (event) {
      console.log(event.type, event.target)
    },
    move (event) {
      position.x += event.dx
      position.y += event.dy

      event.target.style.transform =
        `translate(${position.x}px, ${position.y}px)`
    }
  }
  })

  .resizable({
    edges: { top: true, left: true, bottom: true, right: true },
    listeners: {
      move: function (event) {
        let { x, y } = event.target.dataset

        x = (parseFloat(x) || 0) + event.deltaRect.left
        y = (parseFloat(y) || 0) + event.deltaRect.top

        Object.assign(event.target.style, {
          width: `${event.rect.width}px`,
          height: `${event.rect.height}px`,
          transform: `translate(${x}px, ${y}px)`
        })

        Object.assign(event.target.dataset, { x, y })
      }
    }
  })

  interact('.resizable')
  .draggable({
      inertia: true,
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
    }
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


export default {

    components: {

    }
}
</script>


<style>
    body {
        background-color: #F6F1E3;
    }

    .resizable{
       width: 120px;
        border-radius: 0.75rem;
        padding: 20px;
        margin: 1rem;
        background-color: #29e;
        color: white;
        font-size: 20px;
        font-family: sans-serif;
        overflow: hidden;
        touch-action: none;
        box-sizing: border-box;
        position: absolute;
    }
</style>