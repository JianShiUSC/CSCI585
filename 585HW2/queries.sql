/*
student ID: 7983158453
name: Jian Shi
*/
1. SELECT substr(dob, 1, 4) as year, count(username) FROM users GROUP BY year HAVING year >= '1970';
2. SELECT substr(post_date, 12,2) as hour FROM ads GROUP BY hour HAVING count(ad_id) = (
SELECT max(id) FROM  (SELECT count(ad_id) as id FROM ads GROUP BY substr(post_date, 12,2))
);
3. SELECT count(ad_id) FROM ads WHERE post_date > (SELECT last_logout FROM users WHERE username = 'lhartj') AND category_id = '250';
4. SELECT  cities.city_id, cities.name FROM cities, regions WHERE cities.city_id = regions.city_id GROUP BY regions.city_id HAVING count(region_id) = 
(SELECT max(region_num) FROM (
SELECT count(region_id) as region_num FROM regions GROUP BY regions.city_id
));
5. SELECT name FROM users WHERE username = (
SELECT creator FROM ads WHERE ads.ad_id = (
SELECT ad_id FROM likes GROUP BY ad_id HAVING count(ad_id) = 
(SELECT max(like_num) FROM (
SELECT count(ad_id) as like_num FROM likes GROUP BY ad_id))));
6. SELECT region_id, name FROM regions WHERE regions.region_id = (
SELECT region_id FROM ads GROUP BY region_id HAVING count(region_id) = 
(SELECT max(region_num) FROM
(SELECT count(region_id) as region_num FROM ads GROUP BY region_id)
));
7. SELECT creator FROM ads WHERE substr(post_date, 1, 4) = '2015' GROUP BY creator ORDER BY count(creator) DESC LIMIT 3;
8. SELECT title, price FROM ads WHERE creator = 'bnguyen50' AND post_date = 
(SELECT max(post_date) FROM ads WHERE creator = 'bnguyen50'
);