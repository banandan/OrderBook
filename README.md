# OrderBook
This project implements a matching engine which uses an order book to determine
the order that can be fulfilled. This application reads input from STDIN and produces output
to STDOUT.

Each order is specified in a line with five fields 
OrderType (limit/market/modify/cancel), OrderId (identify an order id), Side (buy/sell), Volume (quantity as int), Price (price as double)

Sample Input
------------
limit 1 buy 10 45.00 <br />
limit 2 buy 15 44.00 <br />
limit 3 buy 3 45.50 <br />
modify 3 buy 2 45.30 <br />
limit 4 sell 5 45.00 <br />
limit 5 buy 5 46.50 <br />

Sample output
----------
match 4 3 2 45.30 <br />
match 4 1 3 45.00 <br />
