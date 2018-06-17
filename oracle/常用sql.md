* 清除缓存
    alter system flush SHARED_POOL;
    alter system flush buffer_cache;
    alter system flush global context;

 EXPLAIN PLAN FOR 
	select   
		fCode,fName,
		fIdCard,phone,
		kindCode,itemCode,
		sumAmount,sumPremium,centralPremium,
		provincePremium,cityPremium,
		townPremium,otherPremium,
		breedingAreaName,species,
		breedingKind,earlAbel 
		from HerdPolicyList where inusreListCode='QDBH322034000020180430000013'  ;
SELECT plan_table_output FROM TABLE(DBMS_XPLAN.DISPLAY('PLAN_TABLE'));