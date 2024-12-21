SELECT p.post_id
FROM post p
JOIN comment c ON p.post_id = c.post_id
WHERE p.title ~ '^[0-9]' AND LENGTH(p.content) > 20
GROUP BY p.post_id
HAVING COUNT(c.post_id) = 2
ORDER BY p.post_id ASC;
