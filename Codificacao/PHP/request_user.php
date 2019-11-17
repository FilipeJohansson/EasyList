<?php

  header("Context-type:application/json");

  require_once("connect.php");

  $EMAIL = $_GET['EMAIL'];

  $query = $pdo->prepare("SELECT
    ID_USUARIO
    FROM `usuario`
    WHERE `EMAIL` = '$EMAIL';");
  $query->execute();

  $response = array();

  while($row = $query->fetch(PDO::FETCH_ASSOC)) {

    array_push($response,
    array(
      'ID_USUARIO' =>$row['ID_USUARIO']
    )
    );

  }

  echo json_encode($response);

?>
