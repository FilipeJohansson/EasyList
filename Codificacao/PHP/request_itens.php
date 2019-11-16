<?php

  header("Context-type:application/json");

  require_once("connect.php");

  $ID_LISTA = $_GET['ID_LISTA'];

  $query = $pdo->prepare("SELECT
    ID_ITEM, STA_CHECK,
    QUANTIDADE, NME_PRODUTO,
    DSC_PRODUTO, UN_MEDIDA
    FROM `item`
    WHERE `ID_LISTA` = '$ID_LISTA'
    ORDER BY STA_CHECK ASC;");
  $query->execute();

  $response = array();

  while($row = $query->fetch(PDO::FETCH_ASSOC)) {

    array_push($response,
    array(
      'ID_ITEM' =>$row['ID_ITEM'],
      'STA_CHECK' =>$row['STA_CHECK'],
      'QUANTIDADE' =>$row['QUANTIDADE'],
      'NME_PRODUTO' =>$row['NME_PRODUTO'],
      'DSC_PRODUTO' =>$row['DSC_PRODUTO'],
      'UN_MEDIDA' =>$row['UN_MEDIDA']
    )
    );

  }

  echo json_encode($response);

?>
