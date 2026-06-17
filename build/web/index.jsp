<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://unpkg.com"></script>
    </head>
    <body>
        <div>TODO write content</div>
        <div id="app">
          <h1>{{ message }}</h1>
          <button @click="reverseMessage">Reverse text</button>
        </div>
        <script>
            const { createApp, ref } = Vue;
            
            // Initialize Vue App
            createApp({
              setup() {
                const message = ref('Hello Vue.js!');

                function reverseMessage() {
                  message.value = message.value.split('').reverse().join('');
                }

                return { message, reverseMessage };
              }
            }).mount('#app'); // Hook Vue into the HTML div
          </script>
    </body>
</html>
