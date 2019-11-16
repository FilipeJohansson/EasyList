<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $ID_ITEM = $_POST['ID_ITEM'];
    $STA_CHECK = $_POST['STA_CHECK'];

    require_once("connect.php");

    $query = $pdo->prepare("UPDATE `item` SET STA_CHECK = '$STA_CHECK' WHERE ID_ITEM = '$ID_ITEM';");

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
