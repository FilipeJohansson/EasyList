<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $UID_USUARIO = $_POST['UID_USUARIO'];

    require_once("connect.php");

    $query = $pdo->prepare("INSERT INTO `usuario` (UID) VALUES ('$UID_USUARIO');");
    $query->execute();

    if ($query) {
      $response['success'] = true;
      $response['message'] = "Successfully";

    } else {
      $response['success'] = false;
      $response['message'] = "Failure";

    }

  } else {
    $response['success'] = false;
    $response['message'] = "Error";

  }

  echo json_encode($response);

?>
