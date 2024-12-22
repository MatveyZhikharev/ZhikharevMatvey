<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Главная страница</title>
  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/gh/yegor256/tacit@gh-pages/tacit-css-1.6.0.min.css"/>
</head>

<body>

<h1>Список книг</h1>
<table>
  <tr>
    <th>Название</th>
    <th>Автор</th>
  </tr>
    <#list articles as article>
      <tr>
        <td>${article.title}</td>
        <#list article.tags as tag>
          <tr>
            <td>${tag}</td>
          </tr>
        </#list>
        <#list article.comments as comment>
          <tr>
            <td>${comment}</td>
          </tr>
        </#list>
      </tr>
    </#list>
</table>

</body>

</html>