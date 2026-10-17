package com.example.traductor_espanol_turco.model

object DatosVocabulario {

    val categorias = listOf(
        Categoria(1, "Animales"),
        Categoria(2, "Juguetes"),
        Categoria(3, "Colores"),
        Categoria(4, "Alimentos")
    )

    val palabras = listOf(
        // Animales
        Palabra(
            id = 1,
            categoriaId = 1,
            espanol = "Gato",
            turco = "Kedi",
            fraseEspanol = "El gato duerme.",
            fraseTurco = "Kedi uyuyor."
        ),
        Palabra(
            id = 2,
            categoriaId = 1,
            espanol = "Perro",
            turco = "Köpek",
            fraseEspanol = "El perro corre.",
            fraseTurco = "Köpek koşuyor."
        ),
        Palabra(
            id = 5,
            categoriaId = 1,
            espanol = "Pájaro",
            turco = "Kuş",
            fraseEspanol = "El pájaro vuela.",
            fraseTurco = "Kuş uçuyor."
        ),
        Palabra(
            id = 6,
            categoriaId = 1,
            espanol = "Pez",
            turco = "Balık",
            fraseEspanol = "El pez nada.",
            fraseTurco = "Balık yüzüyor."
        ),

        // Juguetes
        Palabra(
            id = 3,
            categoriaId = 2,
            espanol = "Pelota",
            turco = "Top",
            fraseEspanol = "Yo juego a la pelota.",
            fraseTurco = "Ben top oynuyorum."
        ),
        Palabra(
            id = 4,
            categoriaId = 2,
            espanol = "Muñeca",
            turco = "Oyuncak bebek",
            fraseEspanol = "Esta es mi muñeca.",
            fraseTurco = "Bu benim oyuncak bebeğim."
        ),
        Palabra(
            id = 7,
            categoriaId = 2,
            espanol = "Auto de juguete",
            turco = "Oyuncak araba",
            fraseEspanol = "Este es mi auto de juguete.",
            fraseTurco = "Bu benim oyuncak arabam."
        ),
        Palabra(
            id = 8,
            categoriaId = 2,
            espanol = "Cometa",
            turco = "Uçurtma",
            fraseEspanol = "La cometa vuela.",
            fraseTurco = "Uçurtma uçuyor."
        ),

        // Colores
        Palabra(
            id = 9,
            categoriaId = 3,
            espanol = "Rojo",
            turco = "Kırmızı",
            fraseEspanol = "La pelota es roja.",
            fraseTurco = "Top kırmızı."
        ),
        Palabra(
            id = 10,
            categoriaId = 3,
            espanol = "Azul",
            turco = "Mavi",
            fraseEspanol = "El cielo es azul.",
            fraseTurco = "Gökyüzü mavi."
        ),
        Palabra(
            id = 11,
            categoriaId = 3,
            espanol = "Verde",
            turco = "Yeşil",
            fraseEspanol = "La manzana es verde.",
            fraseTurco = "Elma yeşil."
        ),
        Palabra(
            id = 12,
            categoriaId = 3,
            espanol = "Amarillo",
            turco = "Sarı",
            fraseEspanol = "El plátano es amarillo.",
            fraseTurco = "Muz sarı."
        ),

        // Alimentos
        Palabra(
            id = 13,
            categoriaId = 4,
            espanol = "Pan",
            turco = "Ekmek",
            fraseEspanol = "Yo como pan.",
            fraseTurco = "Ben ekmek yiyorum."
        ),
        Palabra(
            id = 14,
            categoriaId = 4,
            espanol = "Manzana",
            turco = "Elma",
            fraseEspanol = "Yo como una manzana.",
            fraseTurco = "Ben bir elma yiyorum."
        ),
        Palabra(
            id = 15,
            categoriaId = 4,
            espanol = "Leche",
            turco = "Süt",
            fraseEspanol = "Yo bebo leche.",
            fraseTurco = "Ben süt içiyorum."
        ),
        Palabra(
            id = 16,
            categoriaId = 4,
            espanol = "Queso",
            turco = "Peynir",
            fraseEspanol = "Yo como queso.",
            fraseTurco = "Ben peynir yiyorum."
        )
    )
}