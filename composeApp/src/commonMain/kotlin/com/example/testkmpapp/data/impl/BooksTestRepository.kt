package com.example.testkmpapp.data.impl

import com.example.testkmpapp.domain.BooksRepository
import com.example.testkmpapp.domain.models.Author
import com.example.testkmpapp.domain.models.BookPagingResponse
import com.example.testkmpapp.domain.models.BookPreview
import com.example.testkmpapp.domain.models.Rating
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlin.random.Random

class BooksTestRepository: BooksRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val data by lazy {
        json.decodeFromString<BookPagingResponse>(mockData)
    }

    override suspend fun getBooks(
        query: String,
        genres: List<String>,
        authors: List<String>,
        number: Int,
        offset: Int
    ): BookPagingResponse {
        return withContext(Dispatchers.Default) {
            delay(Random.nextLong(500, 1500))
            data.copy(
                number = number,
                offset = offset
            )
        }
    }

    override suspend fun getBookInfo(id: Int): BookPreview {
        fun buildDesc() = buildString {
            repeat(100) {
                append("This is test description ")
            }
        }
        return withContext(Dispatchers.Default) {
            delay(Random.nextLong(500, 1500))
            val item = data.books.flatMap { it }.first { it.id == id }
            item.copy(
                desc = buildDesc(),
                date = 2020.0,
                numberOfPages = Random.nextDouble(12.0, 557.0),
                authors = item.authors.toMutableList().apply {
                    add(
                        Author(-1, "Eugene Plovotok")
                    )
                }
            )
        }
    }
}

private val mockData = """
    {
        "available": 400,
        "number": 400,
        "offset": 0,
        "books": [
            [
                {
                    "id": 20136320,
                    "title": "Jewell's wholesale price list surplus, March 1944",
                    "subtitle": "surplus, March 1944",
                    "image": "https://covers.openlibrary.org/b/id/11552000-M.jpg",
                    "authors": [
                        {
                            "id": 14593696,
                            "name": "Jewell Nurseries"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 17496594,
                    "title": "The 2007-2012 World Outlook for Ceiling, Wall-Mounted, and Floor-Mounted Refrigeration Coolers with 6,001 to 8,000 BTU Per Hour",
                    "image": "https://covers.openlibrary.org/b/id/2440023-M.jpg",
                    "authors": [
                        {
                            "id": 13033190,
                            "name": "Philip M. Parker"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 13469330,
                    "title": "The bar-tender's guide",
                    "subtitle": "or How to mix all kinds of plain and fancy drinks, containing...directions for mixing all the beverages used in the United States, together with the most popular British, French, German, Italian, Russian, and Spanish recipes; embracing punches, juleps, cobblers, etc. ...",
                    "image": "https://covers.openlibrary.org/b/id/9789375-M.jpg",
                    "authors": [
                        {
                            "id": 13469324,
                            "name": "Jerry Thomas"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 18691528,
                    "title": "Talk Dirty to Me",
                    "subtitle": "An Intimate Philosophy of Sex",
                    "image": "https://covers.openlibrary.org/b/id/240640-M.jpg",
                    "authors": [
                        {
                            "id": 13186574,
                            "name": "Sallie Tisdale"
                        }
                    ],
                    "rating": {
                        "average": 0.7739999890327454
                    }
                }
            ],
            [
                {
                    "id": 14664264,
                    "title": "101 stupid things employees do to sabotage success",
                    "image": "https://covers.openlibrary.org/b/id/2083414-M.jpg",
                    "authors": [
                        {
                            "id": 14664244,
                            "name": "Richard Baisner"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 19886462,
                    "title": "Getting grilled",
                    "image": "https://covers.openlibrary.org/b/id/10758394-M.jpg",
                    "authors": [
                        {
                            "id": 19886456,
                            "name": "Wade Christensen"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 22468934,
                    "title": "Lullaby",
                    "image": "https://covers.openlibrary.org/b/id/4325692-M.jpg",
                    "authors": [
                        {
                            "id": 14109824,
                            "name": "Oakley M. Hall"
                        }
                    ],
                    "rating": {
                        "average": 0.6000000238418579
                    }
                }
            ],
            [
                {
                    "id": 17054132,
                    "title": "Knowing God's Will Made Easier",
                    "image": "https://covers.openlibrary.org/b/id/804348-M.jpg",
                    "authors": [
                        {
                            "id": 13162318,
                            "name": "Mark Water"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 18441670,
                    "title": "Manual of primary eye care",
                    "image": "https://covers.openlibrary.org/b/id/1399613-M.jpg",
                    "authors": [
                        {
                            "id": 18441660,
                            "name": "Narciss Okhravi"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 14414406,
                    "title": "An Indian odyssey",
                    "subtitle": "tribulations, trials and triumphs of Gibson Band of the Mohawk tribe of the Iroquois Confederacy",
                    "image": "https://covers.openlibrary.org/b/id/13264593-M.jpg",
                    "authors": [
                        {
                            "id": 14414402,
                            "name": "Sylvia DuVernet"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 22276330,
                    "title": "Sense and sensibilia",
                    "image": "https://covers.openlibrary.org/b/id/7015771-M.jpg",
                    "authors": [
                        {
                            "id": 16140962,
                            "name": "J. L. Austin"
                        }
                    ],
                    "rating": {
                        "average": 0.8140000104904175
                    }
                }
            ],
            [
                {
                    "id": 14221802,
                    "title": "52, Vol. 2",
                    "image": "https://covers.openlibrary.org/b/id/1729449-M.jpg",
                    "authors": [
                        {
                            "id": 13550308,
                            "name": "Mark Waid"
                        },
                        {
                            "id": 13754444,
                            "name": "Grant Morrison"
                        },
                        {
                            "id": 14102970,
                            "name": "Geoff Johns"
                        },
                        {
                            "id": 14221730,
                            "name": "Greg Rucka"
                        }
                    ],
                    "rating": {
                        "average": 0.7820000052452087
                    }
                }
            ],
            [
                {
                    "id": 23663868,
                    "title": "Instructions for preparing the antemortem and postmortem inspection summary",
                    "image": "https://covers.openlibrary.org/b/id/11843123-M.jpg",
                    "authors": [
                        {
                            "id": 17315684,
                            "name": "United States. Agricultural Research Service. Meat Inspection Division"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 20831538,
                    "title": "The government we choose",
                    "subtitle": "lean, focused, affordable : Governor Weld and Lieutenant Governor Cellucci's plan for downsizing Massachusetts government",
                    "image": "https://covers.openlibrary.org/b/id/9420340-M.jpg",
                    "authors": [
                        {
                            "id": 16327264,
                            "name": "Massachusetts. Governor (1991-1997 : Weld)"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 16804274,
                    "title": "Old St. Nick Carving",
                    "subtitle": "Classic Santas from Wood",
                    "image": "https://covers.openlibrary.org/b/id/1438873-M.jpg",
                    "authors": [
                        {
                            "id": 13045234,
                            "name": "Jeffrey B. Snyder"
                        },
                        {
                            "id": 16804270,
                            "name": "David Sabol"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 23414010,
                    "title": "Adobe Flash animation",
                    "subtitle": "creative storytelling for the Web and TV",
                    "image": "https://covers.openlibrary.org/b/id/11585235-M.jpg",
                    "authors": [
                        {
                            "id": 23414004,
                            "name": "Philip Carrera"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 22026472,
                    "title": "1988 CIIPS public opinion survey",
                    "subtitle": "security, arms control and defence : public attitudes in Canada",
                    "image": "https://covers.openlibrary.org/b/id/5308360-M.jpg",
                    "authors": [
                        {
                            "id": 14343376,
                            "name": "Michael Driedger"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 17999208,
                    "title": "Her last death",
                    "subtitle": "a memoir",
                    "image": "https://covers.openlibrary.org/b/id/8014654-M.jpg",
                    "authors": [
                        {
                            "id": 17999206,
                            "name": "Susanna Sonnenberg"
                        }
                    ],
                    "rating": {
                        "average": 0.6679999828338623
                    }
                }
            ],
            [
                {
                    "id": 13971944,
                    "title": "The Magical Child",
                    "image": "https://covers.openlibrary.org/b/id/5179345-M.jpg",
                    "authors": [
                        {
                            "id": 13971942,
                            "name": "Carol Dimarco"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 19386746,
                    "title": "Braided Dreams",
                    "image": "https://covers.openlibrary.org/b/id/1306819-M.jpg",
                    "authors": [
                        {
                            "id": 15382502,
                            "name": "Richard H Orndorff"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 15359482,
                    "title": "RNP particles, splicing, and autoimmune diseases",
                    "image": "https://covers.openlibrary.org/b/id/2227064-M.jpg",
                    "authors": [
                        {
                            "id": 15359470,
                            "name": "Johannes Schenkel"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 23221406,
                    "title": "Physical chemistry for physicians and biologists",
                    "image": "https://covers.openlibrary.org/b/id/6269974-M.jpg",
                    "authors": [
                        {
                            "id": 23221404,
                            "name": "Ernst Cohen"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 19194142,
                    "title": "Nonmetals (Material Matters/Freestyle Express)",
                    "image": "https://covers.openlibrary.org/b/id/1765701-M.jpg",
                    "authors": [
                        {
                            "id": 13614990,
                            "name": "Carol Baldwin"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 20581680,
                    "title": "New Poems",
                    "image": "https://covers.openlibrary.org/b/id/10644477-M.jpg",
                    "authors": [
                        {
                            "id": 20581654,
                            "name": "Joshua Krugman"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 21776614,
                    "title": "The Ten Essentials for Travel in the Outdoors",
                    "image": "https://covers.openlibrary.org/b/id/4772970-M.jpg",
                    "authors": [
                        {
                            "id": 17383182,
                            "name": "Mountaineers"
                        }
                    ],
                    "rating": {
                        "average": 0.699999988079071
                    }
                }
            ],
            [
                {
                    "id": 17749350,
                    "title": "The Army times book of great land battles",
                    "subtitle": "from the Civil War to the Gulf War",
                    "image": "https://covers.openlibrary.org/b/id/1225191-M.jpg",
                    "authors": [
                        {
                            "id": 17749342,
                            "name": "J. D. Morelock"
                        }
                    ],
                    "rating": {
                        "average": 0.6800000071525574
                    }
                }
            ],
            [
                {
                    "id": 13722086,
                    "title": "Desert queen",
                    "subtitle": "the many lives and loves of Daisy Bates",
                    "image": "https://covers.openlibrary.org/b/id/12177832-M.jpg",
                    "authors": [
                        {
                            "id": 13702832,
                            "name": "Susanna De Vries"
                        }
                    ],
                    "rating": {
                        "average": 0.7139999866485596
                    }
                }
            ],
            [
                {
                    "id": 14917020,
                    "title": "Story pictures of transportation and communication",
                    "image": "https://covers.openlibrary.org/b/id/6782947-M.jpg",
                    "authors": [
                        {
                            "id": 14917012,
                            "name": "John Y. Beaty"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 24166482,
                    "title": "Cracking the SAT U.S. & World History Subject Tests",
                    "image": "https://covers.openlibrary.org/b/id/231005-M.jpg",
                    "authors": [
                        {
                            "id": 13143374,
                            "name": "Princeton Review"
                        }
                    ],
                    "rating": {
                        "average": 0.6859999895095825
                    }
                }
            ],
            [
                {
                    "id": 20139218,
                    "title": "Hardware/software interface for the Stereo-matrix display",
                    "image": "https://covers.openlibrary.org/b/id/9907104-M.jpg",
                    "authors": [
                        {
                            "id": 20139210,
                            "name": "Ian MacDonald Cunningham"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 21526756,
                    "title": "The lithographs of Charles Banks Wilson",
                    "image": "https://covers.openlibrary.org/b/id/1523723-M.jpg",
                    "authors": [
                        {
                            "id": 15721068,
                            "name": "David C. Hunt"
                        }
                    ],
                    "rating": {
                        "average": 1.0
                    }
                }
            ],
            [
                {
                    "id": 17499492,
                    "title": "Reinvent your kitchen",
                    "image": "https://covers.openlibrary.org/b/id/7387067-M.jpg",
                    "authors": [
                        {
                            "id": 13184782,
                            "name": "Christine E. Barnes"
                        }
                    ],
                    "rating": {
                        "average": 0.4000000059604645
                    }
                }
            ],
            [
                {
                    "id": 17306888,
                    "title": "Evidence taken before the commission appointed to investigate charges of alleged malfeasance of the officials of the Yukon territory ...",
                    "image": "https://covers.openlibrary.org/b/id/7332393-M.jpg",
                    "authors": [
                        {
                            "id": 17306864,
                            "name": "Canada. Ogilvie Commission of Inquiry."
                        }
                    ]
                }
            ],
            [
                {
                    "id": 22721690,
                    "title": "Witness the Night",
                    "image": "https://covers.openlibrary.org/b/id/10823544-M.jpg",
                    "authors": [
                        {
                            "id": 17003118,
                            "name": "Kishwar Desai"
                        }
                    ],
                    "rating": {
                        "average": 0.6919999718666077
                    }
                }
            ],
            [
                {
                    "id": 14667162,
                    "title": "Frogs",
                    "image": "https://covers.openlibrary.org/b/id/10555667-M.jpg",
                    "authors": [
                        {
                            "id": 13104488,
                            "name": "Melissa Gish"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 21084294,
                    "title": "Six Sigma ",
                    "subtitle": "The Mcgraw-Hill 36 Hour Course",
                    "image": "https://covers.openlibrary.org/b/id/59564-M.jpg",
                    "authors": [
                        {
                            "id": 13487756,
                            "name": "Greg Brue"
                        },
                        {
                            "id": 21084276,
                            "name": "Greg  Brue"
                        },
                        {
                            "id": 21084282,
                            "name": "Ron  Howes"
                        },
                        {
                            "id": 21084290,
                            "name": "Ron Howes"
                        }
                    ],
                    "rating": {
                        "average": 0.7099999785423279
                    }
                }
            ],
            [
                {
                    "id": 17057030,
                    "title": "Clinical Nutrition in the Under 5s",
                    "subtitle": "A Pocket Guide",
                    "image": "https://covers.openlibrary.org/b/id/3000074-M.jpg",
                    "authors": [
                        {
                            "id": 17057024,
                            "name": "J. W. L. Puntis"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 18444568,
                    "title": "Dash & Lily's book of dares",
                    "image": "https://covers.openlibrary.org/b/id/6649904-M.jpg",
                    "authors": [
                        {
                            "id": 13862656,
                            "name": "Rachel Cohn"
                        }
                    ],
                    "rating": {
                        "average": 0.75
                    }
                }
            ],
            [
                {
                    "id": 23666766,
                    "title": "Attitude",
                    "subtitle": "Life Skills (Attitude Series)",
                    "image": "https://covers.openlibrary.org/b/id/2091715-M.jpg",
                    "authors": [
                        {
                            "id": 23666756,
                            "name": "Living the Good News"
                        }
                    ],
                    "rating": {
                        "average": 0.6000000238418579
                    }
                }
            ],
            [
                {
                    "id": 22279228,
                    "title": "Not in My Bed!",
                    "subtitle": "The Wrong Bed",
                    "image": "https://covers.openlibrary.org/b/id/2409358-M.jpg",
                    "authors": [
                        {
                            "id": 13669890,
                            "name": "Kate Hoffmann"
                        }
                    ],
                    "rating": {
                        "average": 0.7139999866485596
                    }
                }
            ],
            [
                {
                    "id": 18251964,
                    "title": "Punishment Bound (Silver Mink)",
                    "image": "https://covers.openlibrary.org/b/id/2108563-M.jpg",
                    "authors": [
                        {
                            "id": 17935634,
                            "name": "Francine Whittaker"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 19639502,
                    "title": "All in! All in! a selection of Dublin children's traditional street-games with rhymes and music",
                    "subtitle": "a selection of Dublin children's traditional street-games with rhymes and music",
                    "image": "https://covers.openlibrary.org/b/id/9614028-M.jpg",
                    "authors": [
                        {
                            "id": 19639496,
                            "name": "Eilís Brady"
                        }
                    ],
                    "rating": {
                        "average": 0.8500000238418579
                    }
                }
            ],
            [
                {
                    "id": 15612238,
                    "title": "Lucy The Lonely Kitten (Kitten Friends, #7)",
                    "image": "https://covers.openlibrary.org/b/id/436558-M.jpg",
                    "authors": [
                        {
                            "id": 13141020,
                            "name": "Jenny Dale"
                        }
                    ],
                    "rating": {
                        "average": 0.8799999952316284
                    }
                }
            ],
            [
                {
                    "id": 20834436,
                    "title": "Crumple",
                    "image": "https://covers.openlibrary.org/b/id/791302-M.jpg",
                    "authors": [
                        {
                            "id": 14891208,
                            "name": "Dave Cooper"
                        },
                        {
                            "id": 15124196,
                            "name": "David Cooper (undifferentiated)"
                        }
                    ],
                    "rating": {
                        "average": 0.722000002861023
                    }
                }
            ],
            [
                {
                    "id": 16807172,
                    "title": "The 2007-2012 Outlook for Commercial Coin-Operated Laundry Drying Tumblers with More Than 10 Kg (22 Pounds) Load Capacity Excluding Parts, Attachments, and Accessories in the United States",
                    "image": "https://covers.openlibrary.org/b/id/2444718-M.jpg",
                    "authors": [
                        {
                            "id": 13033190,
                            "name": "Philip M. Parker"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 18002106,
                    "title": "Annual report of the Commissioner of Banks for the year ending ... Relating to savings banks and institutions for savings",
                    "subtitle": "Relating to savings banks and institutions for savings",
                    "image": "https://covers.openlibrary.org/b/id/9966173-M.jpg",
                    "authors": [
                        {
                            "id": 14699484,
                            "name": "Massachusetts. Division of Banks and Loan Agencies"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 27251568,
                    "title": "Cattle purchases by Agricultural Adjustment Administration from drought areas June 1934 to February 1935",
                    "image": "https://covers.openlibrary.org/b/id/14601835-M.jpg",
                    "authors": [
                        {
                            "id": 27251566,
                            "name": "Harry Petrie"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 24611842,
                    "title": "Process-based strategic planning",
                    "image": "https://covers.openlibrary.org/b/id/9462165-M.jpg",
                    "authors": [
                        {
                            "id": 24611832,
                            "name": "Richard Kühn"
                        }
                    ],
                    "rating": {
                        "average": 0.6660000085830688
                    }
                }
            ],
            [
                {
                    "id": 23224304,
                    "title": "Evidence-based management of pancreatic malignancy",
                    "image": "https://covers.openlibrary.org/b/id/10579474-M.jpg",
                    "authors": [
                        {
                            "id": 23224292,
                            "name": "Richard K. Orr"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 15169776,
                    "title": "The 826 Quarterly, Vol. 3",
                    "subtitle": "Fall 2004 (826 Quarterly, The)",
                    "image": "https://covers.openlibrary.org/b/id/2125278-M.jpg",
                    "authors": [
                        {
                            "id": 13278986,
                            "name": "Students in Conjunction with 826 Valencia"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 20584578,
                    "title": "EFT for back pain",
                    "subtitle": "a specialized use of emotional freedom techniques",
                    "image": "https://covers.openlibrary.org/b/id/12416321-M.jpg",
                    "authors": [
                        {
                            "id": 20584572,
                            "name": "Gary Craig"
                        }
                    ],
                    "rating": {
                        "average": 0.8700000047683716
                    }
                }
            ],
            [
                {
                    "id": 16557314,
                    "title": "The ingenious John Banvard",
                    "image": "https://covers.openlibrary.org/b/id/7355238-M.jpg",
                    "authors": [
                        {
                            "id": 13138374,
                            "name": "Nan Hayden Agle"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 24419238,
                    "title": "The story of the Cyprus Government Railway",
                    "image": "https://covers.openlibrary.org/b/id/8521179-M.jpg",
                    "authors": [
                        {
                            "id": 24419232,
                            "name": "B. S. Turner"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 21779512,
                    "title": "Motueka",
                    "subtitle": "an archaeological survey",
                    "image": "https://covers.openlibrary.org/b/id/10187306-M.jpg",
                    "authors": [
                        {
                            "id": 21779508,
                            "name": "A. J. Challis"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 17752248,
                    "title": "Sex and Reason",
                    "image": "https://covers.openlibrary.org/b/id/12420719-M.jpg",
                    "authors": [
                        {
                            "id": 13593122,
                            "name": "Richard A. Posner"
                        }
                    ],
                    "rating": {
                        "average": 0.7820000052452087
                    }
                }
            ],
            [
                {
                    "id": 13724984,
                    "title": "Discoveries of the world, from their first original unto the year of Our Lord 1555",
                    "image": "https://covers.openlibrary.org/b/id/8639927-M.jpg",
                    "authors": [
                        {
                            "id": 13724980,
                            "name": "António Galvão"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 22974446,
                    "title": "Crime and community opportunity",
                    "subtitle": "field hearing before the Subcommittee on Housing and Community Opportunity of the Committee on Banking and Financial Services, House of Representatives, One Hundred Fourth Congress, second session, February 22, 1996.",
                    "image": "https://covers.openlibrary.org/b/id/4591333-M.jpg",
                    "authors": [
                        {
                            "id": 13498440,
                            "name": "United States. Congress. House. Committee on Banking and Financial Services. Subcommittee on Housing and Community Opportunity."
                        }
                    ]
                }
            ],
            [
                {
                    "id": 18947182,
                    "title": "Journey Through My Life",
                    "image": "https://covers.openlibrary.org/b/id/1810221-M.jpg",
                    "authors": [
                        {
                            "id": 18947178,
                            "name": "Harry Lewin"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 24169380,
                    "title": "The Best plays of 1928-29 and the Year book of the drama in America",
                    "subtitle": "and the Year book of the drama in America",
                    "image": "https://covers.openlibrary.org/b/id/9531232-M.jpg",
                    "authors": [
                        {
                            "id": 13658644,
                            "name": "Burns Mantle"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 16114852,
                    "title": "Judgment In Blood (Mack Bolan Stonyman, 50)",
                    "image": "https://covers.openlibrary.org/b/id/219387-M.jpg",
                    "authors": [
                        {
                            "id": 13127364,
                            "name": "Don Pendleton"
                        }
                    ],
                    "rating": {
                        "average": 0.699999988079071
                    }
                }
            ],
            [
                {
                    "id": 23907930,
                    "title": "Death and Life in America, Second Edition Biomedicine and Biblical Healing",
                    "subtitle": "Biomedicine and Biblical Healing",
                    "image": "https://covers.openlibrary.org/b/id/12381455-M.jpg",
                    "authors": [
                        {
                            "id": 17441122,
                            "name": "Raymond Downing"
                        },
                        {
                            "id": 23907928,
                            "name": "Farr Curlin"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 17502390,
                    "title": "Indian Leap",
                    "image": "https://covers.openlibrary.org/b/id/10765828-M.jpg",
                    "authors": [
                        {
                            "id": 17502386,
                            "name": "Seth Kanor"
                        }
                    ],
                    "rating": {
                        "average": 0.8799999952316284
                    }
                }
            ],
            [
                {
                    "id": 22724588,
                    "title": "Teaching strategies",
                    "subtitle": "a guide to better instruction",
                    "image": "https://covers.openlibrary.org/b/id/1324337-M.jpg",
                    "authors": [
                        {
                            "id": 13060072,
                            "name": "Abbie H. Brown"
                        },
                        {
                            "id": 14351454,
                            "name": "Richard C. Callahan"
                        },
                        {
                            "id": 14351460,
                            "name": "Michael S. Trevisan"
                        },
                        {
                            "id": 16084932,
                            "name": "Donald C. Orlich"
                        },
                        {
                            "id": 22724568,
                            "name": "Robert J. Harder"
                        }
                    ],
                    "rating": {
                        "average": 0.7020000219345093
                    }
                }
            ],
            [
                {
                    "id": 21337050,
                    "title": "The Generic Names of Moths in the World Vol. VI",
                    "subtitle": "Microlepidoptera (Genetic Names of Moths of the World)",
                    "image": "https://covers.openlibrary.org/b/id/7433433-M.jpg",
                    "authors": [
                        {
                            "id": 19801130,
                            "name": "I. W. B. Nye"
                        },
                        {
                            "id": 21337042,
                            "name": "D. S. Fletcher"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 15853402,
                    "title": "Come to Dust",
                    "image": "https://covers.openlibrary.org/b/id/1294230-M.jpg",
                    "authors": [
                        {
                            "id": 15853388,
                            "name": "Frank Biancamano"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 13282522,
                    "title": "Metabolic Regulation in Mammals (Lifelines (Taylor & Francis).)",
                    "image": "https://covers.openlibrary.org/b/id/1392138-M.jpg",
                    "authors": [
                        {
                            "id": 13282506,
                            "name": "David Gibson"
                        },
                        {
                            "id": 13282514,
                            "name": "Robert A. Harris"
                        }
                    ],
                    "rating": {
                        "average": 1.0
                    }
                }
            ],
            [
                {
                    "id": 17240940,
                    "title": "Polymer Composites for Civil and Structural Engineering",
                    "image": "https://covers.openlibrary.org/b/id/8707870-M.jpg",
                    "authors": [
                        {
                            "id": 17240938,
                            "name": "L. Hollaway"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 14670060,
                    "title": "European proverbs",
                    "subtitle": "in 55 languages, with equivalents in Arabic, Persian, Sanskrit, Chinese and Japanese",
                    "image": "https://covers.openlibrary.org/b/id/11628718-M.jpg",
                    "authors": [
                        {
                            "id": 13876372,
                            "name": "Paczolay, Gyula."
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 21075600,
                    "title": "Pastoral reminiscences",
                    "image": "https://covers.openlibrary.org/b/id/5807581-M.jpg",
                    "authors": [
                        {
                            "id": 17022908,
                            "name": "Shepard Kosciuszko Kollock"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 19892258,
                    "title": "Delegitimizing al-Qaeda",
                    "subtitle": "a jihad-realist approach",
                    "image": "https://covers.openlibrary.org/b/id/8275801-M.jpg",
                    "authors": [
                        {
                            "id": 19892250,
                            "name": "Paul Kamolick"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 17059928,
                    "title": "Nuclear Disasters (World's Worst)",
                    "image": "https://covers.openlibrary.org/b/id/2927647-M.jpg",
                    "authors": [
                        {
                            "id": 13150362,
                            "name": "Rob Alcraft"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 18447466,
                    "title": "African dance education in Ghana",
                    "subtitle": "curriculum and instructional materials for a model Bachelor of Arts (Hons.) Dance in Society",
                    "image": "https://covers.openlibrary.org/b/id/11006023-M.jpg",
                    "authors": [
                        {
                            "id": 18447458,
                            "name": "W. Ofotsu Adinku"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 22282126,
                    "title": "Misdiagnosed",
                    "subtitle": "Was My Wife a Casualty of America's Medical Cold War?",
                    "image": "https://covers.openlibrary.org/b/id/955769-M.jpg",
                    "authors": [
                        {
                            "id": 14419968,
                            "name": "A. Robert Smith"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 14227598,
                    "title": "Philosophy in the Bedroom",
                    "image": "https://covers.openlibrary.org/b/id/9853573-M.jpg",
                    "authors": [
                        {
                            "id": 13430810,
                            "name": "Marquis de Sade"
                        }
                    ],
                    "rating": {
                        "average": 0.6899999976158142
                    }
                }
            ],
            [
                {
                    "id": 23669664,
                    "title": "Dark Eagles",
                    "subtitle": "A History of Top Secret U.S. Aircraft Programs",
                    "image": "https://covers.openlibrary.org/b/id/689159-M.jpg",
                    "authors": [
                        {
                            "id": 13098384,
                            "name": "Curtis Peebles"
                        }
                    ],
                    "rating": {
                        "average": 0.777999997138977
                    }
                }
            ],
            [
                {
                    "id": 27504324,
                    "title": "Birds on fragmented islands persistence in the forests of Java and Bali",
                    "authors": [
                        {
                            "id": 13867400,
                            "name": "Bas van Balen"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 22020676,
                    "title": "Chartist Fiction",
                    "subtitle": "Ernest Jones, Women's Wrongs (Nineteenth Century)",
                    "image": "https://covers.openlibrary.org/b/id/1409328-M.jpg",
                    "authors": [
                        {
                            "id": 14954612,
                            "name": "Ernest Charles Jones"
                        }
                    ],
                    "rating": {
                        "average": 0.6000000238418579
                    }
                }
            ],
            [
                {
                    "id": 13966148,
                    "title": "The impeachment of Andrew Johnson",
                    "image": "https://covers.openlibrary.org/b/id/1475390-M.jpg",
                    "authors": [
                        {
                            "id": 13478908,
                            "name": "Chester G. Hearn"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 23408214,
                    "title": "The Nude",
                    "subtitle": "a New Perspective",
                    "image": "https://covers.openlibrary.org/b/id/9379262-M.jpg",
                    "authors": [
                        {
                            "id": 13114528,
                            "name": "Gill Saunders"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 15353686,
                    "title": "Global Television Marketplace (BFI International Screen Industries)",
                    "image": "https://covers.openlibrary.org/b/id/2031082-M.jpg",
                    "authors": [
                        {
                            "id": 15353676,
                            "name": "Timothy Havens"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 20575884,
                    "title": "One Last Time",
                    "subtitle": "Everything and Nothing",
                    "image": "https://covers.openlibrary.org/b/id/1297966-M.jpg",
                    "authors": [
                        {
                            "id": 19817800,
                            "name": "James M., Ph.D. Butler"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 23227202,
                    "title": "Look There",
                    "subtitle": "Selected Poems",
                    "image": "https://covers.openlibrary.org/b/id/776576-M.jpg",
                    "authors": [
                        {
                            "id": 23227198,
                            "name": "Agi Mishʻol"
                        }
                    ],
                    "rating": {
                        "average": 0.7160000205039978
                    }
                }
            ],
            [
                {
                    "id": 15172674,
                    "title": "Delano Grape Strike",
                    "image": "https://covers.openlibrary.org/b/id/10010290-M.jpg",
                    "authors": [
                        {
                            "id": 13351220,
                            "name": "Stuart A. Kallen"
                        }
                    ],
                    "rating": {
                        "average": 0.6000000238418579
                    }
                }
            ],
            [
                {
                    "id": 24614740,
                    "title": "African American Literary Theory",
                    "subtitle": "A Reader",
                    "image": "https://covers.openlibrary.org/b/id/612321-M.jpg",
                    "authors": [
                        {
                            "id": 24614738,
                            "name": "Winston Napier"
                        }
                    ],
                    "rating": {
                        "average": 0.8960000276565552
                    }
                }
            ],
            [
                {
                    "id": 16560212,
                    "title": "Holism And Evolution",
                    "image": "https://covers.openlibrary.org/b/id/2487883-M.jpg",
                    "authors": [
                        {
                            "id": 16560210,
                            "name": "J. C. Smuts"
                        }
                    ],
                    "rating": {
                        "average": 0.7739999890327454
                    }
                }
            ],
            [
                {
                    "id": 24353290,
                    "title": "Peptide synthesis",
                    "image": "https://covers.openlibrary.org/b/id/10286882-M.jpg",
                    "authors": [
                        {
                            "id": 14108794,
                            "name": "Miklos Bodanszky"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 22965752,
                    "title": "Assessment accommodations for diverse learners",
                    "image": "https://covers.openlibrary.org/b/id/12731555-M.jpg",
                    "authors": [
                        {
                            "id": 22965730,
                            "name": "Shuren Ge"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 20394872,
                    "title": "The geology of part of Northumberland, including the country between Wooler and Coldstream",
                    "subtitle": "(explanation of quarter-sheet 110 S. W., new series, sheet 3)",
                    "image": "https://covers.openlibrary.org/b/id/6349349-M.jpg",
                    "authors": [
                        {
                            "id": 13887530,
                            "name": "W. Gunn"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 14911224,
                    "title": "Organic Body Care Recipes",
                    "image": "https://covers.openlibrary.org/b/id/1938690-M.jpg",
                    "authors": [
                        {
                            "id": 14104080,
                            "name": "Stephanie Tourles"
                        }
                    ],
                    "rating": {
                        "average": 0.8100000023841858
                    }
                }
            ],
            [
                {
                    "id": 16298762,
                    "title": "Reflexology",
                    "subtitle": "A step-by-step practical guide to therapeutic healing with the hands and feet; How to treat common ailments such as colds, stress, headaches, ... and illustrative maps of reflex zones",
                    "image": "https://covers.openlibrary.org/b/id/2994591-M.jpg",
                    "authors": [
                        {
                            "id": 13770984,
                            "name": "Rosalind Oxenford"
                        }
                    ],
                    "rating": {
                        "average": 0.4000000059604645
                    }
                }
            ],
            [
                {
                    "id": 13727882,
                    "title": "The sculptor",
                    "image": "https://covers.openlibrary.org/b/id/9323309-M.jpg",
                    "authors": [
                        {
                            "id": 13454430,
                            "name": "Scott McCloud"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 21520960,
                    "title": "Journeys of the Soul",
                    "image": "https://covers.openlibrary.org/b/id/2565081-M.jpg",
                    "authors": [
                        {
                            "id": 14435746,
                            "name": "Audrey J. Saabye"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 20133422,
                    "title": "Victorian toys",
                    "image": "https://covers.openlibrary.org/b/id/10446194-M.jpg",
                    "authors": [
                        {
                            "id": 17209592,
                            "name": "Mandy Ross"
                        }
                    ],
                    "rating": {
                        "average": 0.9340000152587891
                    }
                }
            ],
            [
                {
                    "id": 18950080,
                    "title": "Argentine sugar politics",
                    "subtitle": "Tucumán and the Generation of Eighty",
                    "image": "https://covers.openlibrary.org/b/id/4399599-M.jpg",
                    "authors": [
                        {
                            "id": 14732120,
                            "name": "Donna J. Guy"
                        }
                    ],
                    "rating": {
                        "average": 0.800000011920929
                    }
                }
            ],
            [
                {
                    "id": 13466432,
                    "title": "Reviews and Perspectives in Physiology 2002",
                    "image": "https://covers.openlibrary.org/b/id/343553-M.jpg",
                    "authors": [
                        {
                            "id": 13466424,
                            "name": "The Physiological Society"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 24172278,
                    "title": "The Psychological Reading",
                    "image": "https://covers.openlibrary.org/b/id/2876312-M.jpg",
                    "authors": [
                        {
                            "id": 24172266,
                            "name": "Dr. A"
                        }
                    ],
                    "rating": {
                        "average": 0.0
                    }
                }
            ],
            [
                {
                    "id": 16117750,
                    "title": "What's New #3 ",
                    "subtitle": "the Magic Years (What's New with Phil and Dixie)",
                    "image": "https://covers.openlibrary.org/b/id/939232-M.jpg",
                    "authors": [
                        {
                            "id": 13997824,
                            "name": "Phil Foglio"
                        }
                    ],
                    "rating": {
                        "average": 0.8059999942779541
                    }
                }
            ],
            [
                {
                    "id": 21339948,
                    "title": "Crustaceans",
                    "image": "https://covers.openlibrary.org/b/id/6485194-M.jpg",
                    "authors": [
                        {
                            "id": 21339942,
                            "name": "Thomas Cornish"
                        }
                    ]
                }
            ],
            [
                {
                    "id": 15856300,
                    "title": "Getting Students to Show Up",
                    "subtitle": "Practical Ideas for Any Eventfrom 10 to 10,000",
                    "image": "https://covers.openlibrary.org/b/id/1166363-M.jpg",
                    "authors": [
                        {
                            "id": 15004560,
                            "name": "Jonathan McKee"
                        }
                    ],
                    "rating": {
                        "average": 0.7200000286102295
                    }
                }
            ],
            [
                {
                    "id": 22727486,
                    "title": "Going nowhere fast",
                    "subtitle": "step off life's treadmills and find peace of mind",
                    "image": "https://covers.openlibrary.org/b/id/5409089-M.jpg",
                    "authors": [
                        {
                            "id": 17716932,
                            "name": "Melvyn Kinder"
                        }
                    ],
                    "rating": {
                        "average": 0.6240000128746033
                    }
                }
            ]
        ]
    }
""".trimIndent()