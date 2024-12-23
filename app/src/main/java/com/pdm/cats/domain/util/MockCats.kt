package com.pdm.cats.domain.util

import com.pdm.cats.data.dto.Cat

//val mockCats = listOf(
//    Cat(
//        createdAt = "2024-01-01T12:34:56Z",
//        id = "cat1",
//        owner = "Owner1",
//        tags = listOf("cute", "fluffy"),
//        updatedAt = "2024-01-02T14:23:45Z",
//        //imageUrl = "data:image/jpeg;base64,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"
//    ),
//    Cat(
//        createdAt = "2024-01-03T08:22:16Z",
//        id = "cat2",
//        owner = "Owner2",
//        tags = listOf("funny", "playful"),
//        updatedAt = "2024-01-04T10:15:30Z",
//       // imageUrl = "https://d2zp5xs5cp8zlg.cloudfront.net/image-83814-800.jpg"
//    ),
//    Cat(
//        createdAt = "2024-01-05T09:45:12Z",
//        id = "cat3",
//        owner = "Owner3",
//        tags = listOf("sleepy", "adorable"),
//        updatedAt = "2024-01-06T11:50:40Z",
//        ///imageUrl = "https://www.rspcasa.org.au/wp-content/uploads/2024/08/Cat-Management-Act-Review-2.png"
//    ),
//    Cat(
//        createdAt = "2024-01-07T07:18:45Z",
//        id = "cat4",
//        owner = "Owner4",
//        tags = listOf("playful", "energetic"),
//        updatedAt = "2024-01-08T09:30:25Z",
//        //imageUrl = "https://petlibro.com/cdn/shop/files/shutterstock_1422948287.jpg?v=1704968235"
//    ),
//    Cat(
//        createdAt = "2024-01-09T06:12:30Z",
//        id = "cat5",
//        owner = "Owner5",
//        tags = listOf("curious", "mischievous"),
//        updatedAt = "2024-01-10T08:45:10Z",
//        //imageUrl = "https://www.aspca.org/sites/default/files/cat-care_general-cat-care_body1-left.jpg"
//    )
//)