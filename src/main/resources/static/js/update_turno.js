window.addEventListener("load", function () {
  //Buscamos y obtenemos el formulario donde estan
  //los datos que el usuario pudo haber modificado del turno
  const formulario = document.querySelector("#update_turno_form");

  formulario.addEventListener("submit", function (event) {
    //let turnoId = document.querySelector('#id').value;

    //creamos un JSON que tendrá los datos del turno
    //a diferencia de un turno nuevo en este caso enviamos el id
    //para poder identificarlo y modificarlo para no cargarlo como nuevo
    const formData = {
      id: document.querySelector("#id").value,
      paciente: {
        id: document.querySelector("#paciente").value,
      },
      odontologo: {
        id: document.querySelector("#odontologo").value,
      },
      fechaHoraCita: document.querySelector("#fechaHoraCita").value,
    };

    //invocamos utilizando la función fetch la API turnos con el método PUT que modificará
    //el turno que enviaremos en formato JSON
    const url = "/turnos";
    const settings = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formData),
    };
    fetch(url, settings).then((response) => response.json());
  });
});

//Es la funcion que se invoca cuando se hace click sobre el id de un turno del listado
//se encarga de llenar el formulario con los datos de el turno
//que se desea modificar
function findBy(id) {
  const url = "/turnos" + "/" + id;
  const settings = {
    method: "GET",
  };
  fetch(url, settings)
    .then((response) => response.json())
    .then((data) => {
      let turno = data;
      document.querySelector("#id").value = turno.id;
      document.querySelector("#paciente").value = turno.paciente.id;
      document.querySelector("#odontologo").value = turno.odontologo.id;

      // Convertir la fecha y hora al formato 'YYYY-MM-DDTHH:MM'
      let fechaHoraCita = new Date(turno.fechaHoraCita);
      let year = fechaHoraCita.getFullYear();
      let month = String(fechaHoraCita.getMonth() + 1).padStart(2, "0");
      let day = String(fechaHoraCita.getDate()).padStart(2, "0");
      let hours = String(fechaHoraCita.getHours()).padStart(2, "0");
      let minutes = String(fechaHoraCita.getMinutes()).padStart(2, "0");
      let fechaHoraFormatted = `${year}-${month}-${day}T${hours}:${minutes}`;
      document.querySelector("#fechaHoraCita").value = fechaHoraFormatted;
      /* // Convertir la fecha y hora al formato 'YYYY-MM-DDTHH:MM'
         let fechaHoraCita = new Date(turno.fechaHoraCita);
         let fechaHoraFormatted = fechaHoraCita.toISOString().slice(0, 16);
      document.querySelector("#fechaHoraCita").value = fechaHoraFormatted;*/
      //document.querySelector("#fechaHoraCita").value = turno.fechaHoraCita;
      //el formulario por default esta oculto y al editar se habilita
      document.querySelector("#div_turno_updating").style.display = "block";
    })
    .catch((error) => {
      alert("Error: " + error);
    });
}