window.addEventListener("load", function () {
  const formulario = document.querySelector("#registroForm");

  formulario.addEventListener("submit", function (event) {
    const formData = {
      username: document.querySelector("#username").value,
      password: document.querySelector("#password").value,
      role: document.querySelector("#role").value,
    };

    //invocamos utilizando la función fetch la API turnos con el método POST que guardará
    //el usuario que enviaremos en formato JSON
    const url = "/register/user";
    const settings = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formData),
    };

    fetch(url, settings)
      .then((response) => response.json())
      .then((data) => {
        //Si no hay ningun error se muestra un mensaje diciendo que el usuario
        //se agrego bien
        let successAlert =
          '<div class="alert alert-success alert-dismissible">' +
          '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
          "<strong></strong> Usuario Guardado </div>";

        document.querySelector("#response").innerHTML = successAlert;
        document.querySelector("#response").style.display = "block";
        resetUploadForm();
      })
      .catch((error) => {
        //Si hay algun error se muestra un mensaje diciendo que el usuario
        //no se pudo guardar y se intente nuevamente
        let errorAlert =
          '<div class="alert alert-danger alert-dismissible">' +
          '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
          "<strong> Error intente nuevamente</strong> </div>";

        document.querySelector("#response").innerHTML = errorAlert;
        document.querySelector("#response").style.display = "block";
        //se dejan todos los campos vacíos por si se quiere ingresar otro usuario
        resetUploadForm();
      });
  });

  function resetUploadForm() {
    document.querySelector("#username").value = "";
    document.querySelector("#password").value = "";
    document.querySelector("#role").value = "";
  }
});
