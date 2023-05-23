document.addEventListener('DOMContentLoaded', function () {
    let chartDom = document.getElementById('historyChart');
    let myChart = echarts.init(chartDom);
    let option;

    // Function to fetch request count data and update the chart
    function fetchRequestCountAndUpdateChart() {
        $.ajax({
            url: '/api/v1/request/created-history',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                let xAxisData = Object.keys(data);
                let seriesData = Object.values(data);
                option = {
                    xAxis: {
                        type: 'category',
                        data: xAxisData
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            data: seriesData,
                            type: 'line',
                            smooth: true
                        }
                    ]
                };

                // Update the chart with the new data
                option && myChart.setOption(option);
            },
            error: function (xhr, textStatus, error) {
                console.error(error);
                // Handle error condition
            }
        });
    }

    // Call the fetch request count function to initially populate the chart
    fetchRequestCountAndUpdateChart();

    // Optionally, you can set up an interval to periodically update the chart with fresh data
    // setInterval(fetchRequestCountAndUpdateChart, 5000); // Update every 5 seconds (adjust as needed)
});
