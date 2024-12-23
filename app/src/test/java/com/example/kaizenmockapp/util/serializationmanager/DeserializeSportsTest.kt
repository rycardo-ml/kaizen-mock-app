package com.example.kaizenmockapp.util.serializationmanager

import com.example.kaizenmockapp.util.SerializationManager
import org.junit.Assert
import org.junit.Test

class DeserializeSportsTest {

    private val manager = SerializationManager()

    @Test
    fun `test normal deserialize`() {
        val sportsDto = manager.deserializeSports(sports)
        Assert.assertEquals(1, sportsDto.size)

        val sportDto = sportsDto[0]
        Assert.assertEquals("BASK", sportDto.id)
        Assert.assertEquals("BASKETBALL", sportDto.name)
        Assert.assertEquals(4, sportDto.events.size)
    }

    @Test
    fun `test d as sport and not name deserialize`() {
        val sportsDto = manager.deserializeSports(sportsBroke)
        Assert.assertEquals(2, sportsDto.size)

        val volleyDto = sportsDto[0]
        Assert.assertEquals("VOLL", volleyDto.id)
        Assert.assertEquals("VOLLEYBALL", volleyDto.name)
        Assert.assertEquals(1, volleyDto.events.size)


        val soccerDto = sportsDto[1]
        Assert.assertEquals("FOOT", soccerDto.id)
        Assert.assertEquals("SOCCER", soccerDto.name)
        Assert.assertEquals(14, soccerDto.events.size)
    }
}

private val sportsBroke = """
    [
        {
            "d": "VOLLEYBALL",
            "e": [
                {
                    "i": "48068639",
                    "si": "VOLL",
                    "tt": 1733725080,
                    "d": "France (W) - Japan (W)",
                    "sh": "France (W) - Japan (W)"
                }
            ],
            "i": "VOLL"
        },
        {
            "d": [
                {
                    "i": "FOOT",
                    "d": "SOCCER",
                    "e": [
                        {
                            "d": "Greece - Spain",
                            "i": "22911144",
                            "si": "FOOT",
                            "sh": "Greece - Spain",
                            "tt": 1657944684.0331297
                        },
                        {
                            "d": "Germany - Liechtenstein",
                            "i": "22915152",
                            "si": "FOOT",
                            "sh": "Germany - Liechtenstein",
                            "tt": 1637386787.6128416
                        },
                        {
                            "d": "Romania - Iceland",
                            "i": "22915155",
                            "si": "FOOT",
                            "sh": "Romania - Iceland",
                            "tt": 1655821073.3019047
                        },
                        {
                            "d": "Ireland - Portugal",
                            "i": "22926541",
                            "si": "FOOT",
                            "sh": "Ireland - Portugal",
                            "tt": 1656354182.4533782
                        },
                        {
                            "d": "Slovakia - Slovenia",
                            "i": "23104199",
                            "si": "FOOT",
                            "sh": "Slovakia - Slovenia",
                            "tt": 1661053907.5819564
                        },
                        {
                            "d": "Ecuador - Venezuela",
                            "i": "22949302",
                            "si": "FOOT",
                            "sh": "Ecuador - Venezuela",
                            "tt": 1642449155.5593827
                        },
                        {
                            "d": "Reboceros de La Piedad - Cafetaleros de Chiapas",
                            "i": "23234739",
                            "si": "FOOT",
                            "sh": "Reboceros de La Piedad - Cafetaleros de Chiapas",
                            "tt": 1648832390.5171585
                        },
                        {
                            "d": "Anguilla U20 - Saint Martin U20",
                            "i": "23249195",
                            "si": "FOOT",
                            "sh": "Anguilla U20 - Saint Martin U20",
                            "tt": 1666118291.031325
                        },
                        {
                            "d": "Pontypridd Town (W) - Barry Town United (W)",
                            "i": "23245398",
                            "si": "FOOT",
                            "sh": "Pontypridd Town (W) - Barry Town United (W)",
                            "tt": 1671478243.188033
                        },
                        {
                            "d": "CA Independiente de la Chorrera (W) - CD Universitario (W)",
                            "i": "23249669",
                            "si": "FOOT",
                            "sh": "CA Independiente de la Chorrera (W) - CD Universitario (W)",
                            "tt": 1664078059.6089172
                        },
                        {
                            "d": "West Ham United (Skripp) Esports - Eintracht Frankfurt (Kiser) Esports",
                            "i": "23248669",
                            "si": "FOOT",
                            "sh": "West Ham United (Skripp) Esports - Eintracht Frankfurt (Kiser) Esports",
                            "tt": 1637957167.9993155
                        },
                        {
                            "d": "Olympique Marseille (Alback) Esports - Olympique Lyon (Member) Esports",
                            "i": "23248689",
                            "si": "FOOT",
                            "sh": "Olympique Marseille (Alback) Esports - Olympique Lyon (Member) Esports",
                            "tt": 1645464898.3414605
                        },
                        {
                            "d": "Olympique Lyon (Member) Esports - Bayer 04 Leverkusen (Fastic) Esports",
                            "i": "23248663",
                            "si": "FOOT",
                            "sh": "Olympique Lyon (Member) Esports - Bayer 04 Leverkusen (Fastic) Esports",
                            "tt": 1647983337.685169
                        },
                        {
                            "d": "Olympique Marseille (Alback) Esports - Eintracht Frankfurt (Kiser) Esports",
                            "i": "23248664",
                            "si": "FOOT",
                            "sh": "Olympique Marseille (Alback) Esports - Eintracht Frankfurt (Kiser) Esports",
                            "tt": 1653047390.0282514
                        }
                    ]
                }
            ]
        }
    ]
""".trimIndent()

private val sports = """
    [
    {
    "e": [
      {
        "tt": 1731277860,
        "d": "Nelson Giants - Bay Hawks",
        "sh": "Nelson Giants - Bay Hawks",
        "si": "BASK",
        "i": "49613422"
      },
      {
        "d": "Ateneo Blue Eagles - NU Bulldogs",
        "i": "49905565",
        "sh": "Ateneo Blue Eagles - NU Bulldogs",
        "tt": 1724838180,
        "si": "BASK"
      },
      {
        "d": "Pasay Voyagers - Binan Tatak Gel",
        "i": "49905794",
        "sh": "Pasay Voyagers - Binan Tatak Gel",
        "si": "BASK",
        "tt": 1721676480
      },
      {
        "i": "49905807",
        "sh": "Sungkyunkwan University - Myongii University",
        "tt": 1723388220,
        "d": "Sungkyunkwan University - Myongii University",
        "si": "BASK"
      }
    ],
    "i": "BASK",
    "d": "BASKETBALL"
  }
  ]
""".trimIndent()