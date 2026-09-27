# MusicStream (Android + Kotlin)

App rahisi ya ku-**stream** muziki (sio kupakua/download). Imetumia:

- **Kotlin + Jetpack Compose** — UI
- **Audius API** (https://audius.org) — chanzo cha muziki wa bure, wazi (open-source, hauhitaji API key/malipo). Unaweza kutafuta msanii/wimbo wowote uliopo kwenye Audius na kuusikiliza moja kwa moja (stream).
- **Media3 ExoPlayer** — anayecheza sauti (streaming player)
- **Retrofit + Gson** — kuongea na API
- **Coil** — kupakia picha za albam

## Kuhusu VIDEO
Hakuna API ya bure inayoruhusu "streaming" ya video za muziki moja kwa moja ndani ya app bila kibali/malipo (mf. YouTube haitoi hivyo bure kihalali). Kwa hiyo kwenye kila wimbo kuna kitufe **"Tazama video"** kinachofungua utafutaji wa YouTube kwenye browser/app ya YouTube kwa jina la msanii+wimbo huo — bado ni "streaming" (sio download), lakini kinafunguka nje ya app.

## Jinsi ya kuitumia
1. Pakua/clone folder hii nzima.
2. Fungua kwa **Android Studio** (Hedgehog au mpya zaidi) — "Open an existing project".
3. Acha Gradle i-sync peke yake (itahitaji mtandao mara ya kwanza kupakua dependencies).
4. Bonyeza **Run ▶** kwenye emulator au simu halisi.

## Muundo wa faili muhimu
- `MainActivity.kt` — skrini kuu (search + orodha ya nyimbo)
- `MainViewModel.kt` — mantiki ya kutafuta na kucheza muziki (ExoPlayer)
- `network/AudiusApi.kt` na `RetrofitClient.kt` — mawasiliano na Audius API
- `model/Track.kt` — muundo wa data

## Kubadilisha "discovery node" ya Audius
Audius ina "nodes" kadhaa (servers). Kama `discoveryprovider.audius.co` haifanyi kazi siku fulani, fungua `RetrofitClient.kt` na ubadilishe `currentHost` na moja ya nodes mbadala zilizoachwa kama maoni pale.

## Ikiwa unataka kuongeza vitu vingine baadaye
- Orodha ya "vipendwa" (favorites) — hifadhi local (Room database)
- "Playlists" za mtumiaji
- Player ya chini inayoendelea kuonekana (mini-player) hata ukibadili skrini
