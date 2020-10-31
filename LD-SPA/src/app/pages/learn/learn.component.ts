import { Component, OnInit } from '@angular/core';
import { Chart } from 'node_modules/chart.js';


@Component({
  selector: 'app-learn',
  templateUrl: './learn.component.html',
  styleUrls: ['./learn.component.css']
})
export class LearnComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    var ctx = document.getElementById('PieChart');
    var PieChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: ['VWL', 'Statistik', 'Agiles Projektmanagement'],
            datasets: [{
                label: 'minutes',
                data: [205, 295, 224],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                xAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
  }
 

}
