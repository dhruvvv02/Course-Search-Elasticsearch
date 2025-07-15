# 🧠 Course Search App (Spring Boot + Elasticsearch)

A Spring Boot application that uses Elasticsearch 8.12.2 to provide full-text search, autocomplete suggestions, and fuzzy matching for course data.

---

## 📦 Features

- Full-text search on course `title` and `description`
- Filtering by category, type, age, price, and start date
- Sorting (price ascending/descending, session date)
- Autocomplete suggestions via **Completion Suggester**
- Fuzzy search to support typo tolerance (e.g., `"robots"` → `"Robotics 101"`)

---

## 🚀 Getting Started

### 🐳 1. Launch Elasticsearch with Docker

Make sure Docker is installed. Then run:

```terminal
docker compose up 
```

This starts Elasticsearch 8.12.2 on `http://localhost:9200`.

If you don’t have a `docker-compose.yml` yet:

```yaml
version: '3.8'
services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.12.2
    container_name: elasticsearch
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
    ports:
      - "9200:9200"
```

---

### ☕ 2. Build and Run the Spring Boot Application

```terminal
mvn spring-boot:run
```

By default, the app runs on:  
`http://localhost:8080`

---

## 📊 Populate Sample Data

Upon startup, the `DataLoader` reads `sample-courses.json` from `src/main/resources` and indexes the courses to Elasticsearch.

Ensure the `courses` index has a **completion field** for autocomplete:


---

## 🔍 API Endpoints

### 1. Search Courses

```http
GET /api/search
```

#### Query Parameters:

| Param         | Type    | Description                                 |
|---------------|---------|---------------------------------------------|
| `q`           | string  | Text to search in `title` and `description` |
| `category`    | string  | Filter by category                          |
| `type`        | string  | Filter by type                              |
| `minAge`      | number  | Minimum age                                 |
| `maxAge`      | number  | Maximum age                                 |
| `minPrice`    | number  | Minimum price                               |
| `maxPrice`    | number  | Maximum price                               |
| `startDate`   | ISODate | Filter courses starting after this date     |
| `sort`        | string  | `priceAsc`, `priceDesc`, or default         |
| `page`        | number  | Page number (0-indexed)                     |
| `size`        | number  | Page size                                   |

#### Example Request:

```http
"http://localhost:8080/api/search?q=math&type=COURSE&minAge=8&sort=priceDesc&page=0&size=5"
```

---

### 2. Autocomplete Suggestions

```http
GET /api/search/suggest?q={partialTitle}
```

#### Example:

```
"http://localhost:8080/api/search/suggest?q=sto"
```

#### Response:

```json
[
  "Story Builders",
  "Storytime Reading Circle"
]
```

---

### 3. Fuzzy Search Examples

Typos are tolerated in search.

#### Example:

```
"http://localhost:8080/api/search?q=robots"
```

#### Response:

```json
[
  {
    "title": "Robotics 101",
    "description": "An engaging course designed to inspire and educate.",
    ...
  }
]
```

---

## 🧪 Sample Data

The `sample-courses.json` contains 50 mock courses with fields like:

```json
{
  "id": "course-1",
  "title": "Fun with Algebra",
  "description": "Intro to algebra for kids",
  "category": "Math",
  "type": "COURSE",
  "gradeRange": "3rd–5th",
  "minAge": 8,
  "maxAge": 10,
  "price": 49.99,
  "nextSessionDate": "2025-07-20T14:00:00Z",
  "suggest": ["Fun with Algebra"]
}
```

---

## 🧠 Architecture

```
Spring Boot
 └── CourseController
 └── CourseService
         ├── ElasticsearchClient
         └── JSON Mapper (ObjectMapper)
```

---

## ✅ Commit History Guidelines

| Commit Message                                      | Purpose                            |
|----------------------------------------------------|------------------------------------|
| `Add CourseDocument model`                         | Domain model definition            |
| `Configure Elasticsearch client and ObjectMapper`  | Setup config                       |
| `Implement indexing logic in DataLoader`           | Populate sample data               |
| `Add search endpoint with filter and sort`         | Core search logic                  |
| `Implement suggest endpoint with completion suggester` | Autocomplete feature             |
| `Enable fuzzy search in CourseService`             | Fuzzy matching support             |

---

## 🙌 Author

**Dhruv** – [GitHub](https://github.com/dhruvvv02)

---

