package com.example.cognittiveassesmenttests.cardsTest

/*import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardScreen(
    cardViewModel: CardViewModel
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            cardViewModel.items.forEach { card ->
                DragTarget(
                    dataToDrop = card,
                    viewModel = cardViewModel
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dp(screenWidth / 5f))
                            .clip(RoundedCornerShape(15.dp))
                            .shadow(5.dp, RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center,
                    ){
                    }
                }
            }
        }
        AnimatedVisibility(
            cardViewModel.isCurrentlyDragging,
            enter = slideInHorizontally (initialOffsetX = {it})
        ) {
            DropItem<CardUiItem>(
                modifier = Modifier
                    .size(Dp(screenWidth / 3.5f))
            ) { isInBound, cardItem ->
                if(cardItem != null){
                    LaunchedEffect(key1 = cardItem){
                        cardViewModel.addCard(cardItem)
                    }
                }
                if(isInBound){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                1.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(Color.Gray.copy(0.5f), RoundedCornerShape(15.dp))
                        ,
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Add Card",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                    }
                }else{
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(
                                Color.Black.copy(0.5f),
                                RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Add Card",
                            style = MaterialTheme.typography.body1,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
            ,
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .padding(bottom = 100.dp)
                ,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                Text(
                    text = "Added Persons",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                cardViewModel.addedCards.forEach { card ->
                }
            }
        }
    }
}
*/