--Name: Jian Shi--
--USCID: 7983158453--

a)SELECT L.lion_id FROM lion L WHERE SDO_FILTER(L.vertice_coord, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(150, 200, 400, 350)), 'querytype = WINDOW') = 'TRUE';

b)SELECT L.lion_id FROM lion L, pond P WHERE SDO_WITHIN_DISTANCE(L.vertice_coord, P.vertice_coord, 'distance = 150') = 'TRUE' AND P.pond_id = 'P1';

d)SELECT P.pond_id, SDO_NN_DISTANCE(1) distance FROM pond P, lion L WHERE L.lion_id = 'L2' AND SDO_NN(P.vertice_coord, L.vertice_coord, 'sdo_num_res = 3', 1) = 'TRUE' ORDER BY distance;

f)SELECT L.lion_id, P.pond_id FROM TABLE(SDO_JOIN('lion', 'vertice_coord', 'pond', 'vertice_coord', 'mask=INSIDE'))c, lion L, pond P WHERE c.rowid1 = L.rowid AND c.rowid2 = P.rowid;

h)SELECT DISTINCT region_id FROM TABLE(SDO_JOIN('region', 'vertice_coord', 'lion','vertice_coord','MASK=CONTAINS')) C, region, lion WHERE C.ROWID1=region.ROWID AND C.ROWID2=lion.ROWID AND region_id NOT IN (SELECT region_id FROM TABLE(SDO_JOIN('region','vertice_coord','pond','vertice_coord','MASK=ANYINTERACT')) C, region, pond WHERE C.ROWID1=region.ROWID AND C.ROWID2=pond.ROWID);

i)SELECT L.lion_id FROM LION L WHERE L.lion_id NOT IN (SELECT L.lion_id from TABLE(SDO_JOIN('lion','vertice_coord','ambulancearea','vertice_coord','MASK=ANYINTERACT')) C, lion L, ambulancearea A WHERE C.ROWID1=L.ROWID AND C.ROWID2=A.ROWID);