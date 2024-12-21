SELECT p.post_id
FROM post p
LEFT JOIN comment c ON c.post_id = p.post_id
GROUP BY p.post_id
HAVING COUNT(c.post_id) <= 1
ORDER BY p.post_id ASC
LIMIT 10;
