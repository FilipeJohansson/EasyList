<?php

  header("Context-type:application/json");

  require_once("connect.php");

  $ID_LISTA = $_GET['ID_LISTA'];

  $query = $pdo->prepare("SELECT
    ID_ITEM, NME_PRODUTO,
    QUANTIDADE, STA_CHECK,
    UN_MEDIDA
    FROM `item`
    WHERE `ID_LISTA` = '$ID_LISTA';");
  $query->execute();

  $response = array();

  while($row = $query->fetch(PDO::FETCH_ASSOC)) {

    array_push($response,
    array(
      'ID_ITEM' =>$row['ID_ITEM'],
      'NME_PRODUTO' =>$row['NME_PRODUTO'],
      'QUANTIDADE' =>$row['QUANTIDADE'],
      'STA_CHECK' =>$row['STA_CHECK'],
      'UN_MEDIDA' =>$row['UN_MEDIDA'],
    )
    );

  }

  echo json_encode($response);

?>
