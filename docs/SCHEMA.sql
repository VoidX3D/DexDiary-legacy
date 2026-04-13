CREATE TABLE entries (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    date TEXT UNIQUE NOT NULL,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    mood TEXT NOT NULL,
    tags TEXT,
    word_count INTEGER NOT NULL,
    char_count INTEGER NOT NULL,
    reading_time INTEGER NOT NULL,
    has_image INTEGER DEFAULT 0,
    has_audio INTEGER DEFAULT 0,
    has_location INTEGER DEFAULT 0,
    created_at INTEGER NOT NULL,
    updated_at INTEGER NOT NULL,
    is_excused INTEGER DEFAULT 0,
    excuse_text TEXT
);

CREATE TABLE user_stats (
    id INTEGER PRIMARY KEY CHECK (id = 1),
    total_points INTEGER DEFAULT 0,
    available_points INTEGER DEFAULT 0,
    current_streak INTEGER DEFAULT 0,
    longest_streak INTEGER DEFAULT 0,
    last_entry_date TEXT,
    total_words INTEGER DEFAULT 0,
    total_entries INTEGER DEFAULT 0,
    total_images INTEGER DEFAULT 0,
    total_audio INTEGER DEFAULT 0,
    total_locations INTEGER DEFAULT 0
);

CREATE TABLE point_transactions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    date TEXT NOT NULL,
    type TEXT NOT NULL,
    points INTEGER NOT NULL,
    multiplier REAL NOT NULL,
    note TEXT
);

CREATE TABLE inventory (
    item_id TEXT PRIMARY KEY,
    quantity INTEGER NOT NULL,
    purchased_at INTEGER NOT NULL,
    expires_at INTEGER
);

CREATE TABLE missed_days (
    date TEXT PRIMARY KEY,
    excuse_text TEXT,
    excused_at INTEGER,
    freeze_used INTEGER DEFAULT 0
);

CREATE TABLE media (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    entry_id INTEGER NOT NULL,
    type TEXT NOT NULL,
    uri TEXT NOT NULL,
    thumbnail_uri TEXT,
    FOREIGN KEY(entry_id) REFERENCES entries(id) ON DELETE CASCADE
);

CREATE TABLE locations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    entry_id INTEGER NOT NULL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    place_name TEXT,
    FOREIGN KEY(entry_id) REFERENCES entries(id) ON DELETE CASCADE
);

CREATE TABLE unlocked_themes (
    theme_id TEXT PRIMARY KEY,
    unlocked_at INTEGER NOT NULL,
    is_rental INTEGER DEFAULT 0,
    expires_at INTEGER
);

CREATE TABLE active_powerups (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type TEXT NOT NULL,
    activated_at INTEGER NOT NULL,
    expires_at INTEGER
);

CREATE TABLE ai_logs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    timestamp INTEGER NOT NULL,
    provider TEXT NOT NULL,
    model TEXT NOT NULL,
    prompt_type TEXT NOT NULL,
    input_tokens INTEGER,
    output_tokens INTEGER,
    total_tokens INTEGER,
    processing_time_ms INTEGER,
    status TEXT
);

CREATE TABLE achievements (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    is_unlocked INTEGER DEFAULT 0,
    unlocked_at INTEGER,
    icon TEXT
);
