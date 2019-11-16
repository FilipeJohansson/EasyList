<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $ID_ITEM = $_POST['ID_ITEM'];
    $STA_CHECK = $_POST['STA_CHECK'];

    require_once("connect.php");

    $query = $pdo->prepare("UPDATE `item` SET STA_CHECK = '$STA_CHECK' WHERE ID_ITEM = '$ID_ITEM';");

    $query->execute();

    if ($query) {
      $response['success'] = true;
      $response['message'] = "Item atualizado";

    } else {
      $response['success'] = false;
      $response['message'] = "Erro ao atualizar item";

    }

  } else {
    $response['success'] = false;
    $response['message'] = "Erro na solicitação";

  }

  echo json_encode($response);

?>
