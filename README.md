# 📱 Planer App — Deep Link Navigation

Мобільний застосунок для управління задачами з підтримкою deep links, навігації та тестування.

---

## 🚀 Функціонал

- 📋 Список задач
- ➕ Додавання задач
- 👤 Профіль користувача
- ✔ Виконані задачі
- 📊 Статистика
- 🔐 Безпека (біометрія)
- 🐞 Debug screen для тестування deep links

---

## 🔗 Deep Links

Підтримуються наступні URL:

| URL | Опис |
|-----|------|
| `myapp://home` | Головний екран |
| `myapp://tasks/123` | Деталі задачі |
| `myapp://tasks?filter=completed` | Список задач з фільтром |
| `myapp://invite/ABC123` | Екран запрошення |
| `https://myapp.com/tasks/123` | Деталі задачі (HTTPS) |

---

## 🧠 Архітектура

- **MVVM (спрощено)**
- **Jetpack Compose**
- **Navigation Compose**
- **StateFlow для навігації**
- **Repository pattern**

---

## 🔧 DeepLinkRouter

Основний компонент для обробки URL:

- Парсинг (`parseURL`)
- Обробка (`handle`)
- Навігація через `StateFlow`

---

## 🐞 Debug Screen

Додатковий екран для тестування:

- Ввід URL вручну
- Швидкі кнопки:
  - Task Detail
  - Filter
- Перевірка роботи deep links без adb

---

## 🧪 Тести

Реалізовано 12 unit-тестів:

- ✅ parseURL
- ✅ handle
- ✅ navigate
- ✅ edge cases (invalid URL)
- ✅ share link

---

## ▶ Як запустити

1. Відкрити проєкт в Android Studio
2. Запустити емулятор або пристрій
3. Run ▶

---

## 🔗 Тестування deep links (ADB)

```bash
adb shell am start -a android.intent.action.VIEW -d "myapp://tasks/1"
