<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $UID_USUARIO = $_POST['UID_USUARIO'];
    $EMAIL = $_POST['EMAIL'];

    require_once("connect.php");

    $query = $pdo->prepare("INSERT INTO `usuario` (UID, EMAIL) VALUES ('$UID_USUARIO', '$EMAIL');");
    $query->execute();

    if ($query) {
      $response['success'] = true;
      $response['message'] = "Usuário cadastrado";

    } else {
      $response['success'] = false;
      $response['message'] = "Erro ao cadastrar usuário";

    }

  } else {
    $response['success'] = false;
    $response['message'] = "Erro na solicitação";

  }

  echo json_encode($response);

?>
