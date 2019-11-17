<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $ID_ITEM = $_POST['ID_ITEM'];

    require_once("connect.php");

    $query = $pdo->prepare("DELETE FROM `item` WHERE `ID_ITEM` = '$ID_ITEM';");
    $query->execute();

    if ($query) {
      $response['success'] = true;
      $response['message'] = "Item deletado";

    } else {
      $response['success'] = false;
      $response['message'] = "Erro ao deletar item";

    }

  } else {
    $response['success'] = false;
    $response['message'] = "Erro na solicitação";
  }

  echo json_encode($response);

?>
