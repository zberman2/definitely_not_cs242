# Module used to accumulate statistics regarding a graph
module Stats
  require_relative 'edge'
  require_relative 'graph'
  require_relative 'metro'
  require_relative 'vertex'

  private

  # Called once a shortest path has been found to target
  # Backtracks through the previous values to compile a list
  # of metro downcase names
  # @param target ending Vertex
  # @return Sequence of port names corresponding to the shortest path
  def route(target)
    current = target
    sequence = Array.new
    until current.previous.nil?
      # add vertices to sequence
      sequence << current.metro.name.downcase
      current = current.previous
    end
    # add the source to sequence
    sequence << current.metro.name.downcase
    # return sequence from source to target
    sequence.reverse!
  end

  # set up helper method for Dijkstra
  # sets up a list of all vertices and initializes the previous
  # values to nil and the distances to the starting distances
  # @param graph reference to state of the map
  # @param source Start vertex
  # @return list of vertices
  def init_dijkstra(graph, source)
    vertex_list = Array.new # set up a vertex queue
    graph.vertices.each do |_, vertex|
      vertex.distance = (2**(0.size * 8 -2) -1) # max distance
      vertex.previous = nil # undefined previous vertex
      vertex_list << vertex # add vertex to queue
    end
    source.distance = 0 # distance from source-source = 0
    vertex_list
  end

  # relax operation for edges
  # reset a vertex's distance if a shorter path exists
  # @param neighbor vertex to reset
  # @param new_distance replacement distance for neighbor
  def relax(neighbor, new_distance, current)
    # relax edge and set previous
    neighbor.distance = new_distance
    neighbor.previous = current
  end

  public

  # finds the sequence of metros corresponding the the shortest
  # path between the source and target vertices
  # @param graph reference to state of the map
  # @param source Start vertex
  # @param target End vertex
  def dijkstra(graph, source, target)
    # set up list of vertices over which to iterate
    vertex_list = init_dijkstra(graph, source)

    # loop until no vertices unvisited
    until vertex_list.length == 0
      # pick minimum distance from the list (source on first iteration)
      current = vertex_list.min_by{|vertex| vertex.distance}
      vertex_list.delete(current) # remove vertex

      return route(target) if current == target # found our route

      # loop through current vertex's neighbors
      current.edges.each do |edge|
        neighbor = edge.vertex
        new_distance = current.distance + edge.distance
        # relax if there is a tense edge (shorter path exists)
        if new_distance < neighbor.distance
          relax(neighbor, new_distance, current)
        end
      end
    end
  end

  # helper method for shortest_path
  # locates the source and target vertices and calls dijkstra
  # @return route_details of shortest path
  def shortest_route(graph, ports)
    source = graph.vertices[graph.metros[ports[0]]]
    target = graph.vertices[graph.metros[ports[1]]]
    sequence = dijkstra(graph, source, target)
    graph.route_details(sequence)
  end

  # prompts the user for a set of 2 metros and uses dijkstra's algorithm
  # to find the shortest path in km between them
  # @param graph reference to state of the map
  # @return list of metros/route info about shortest route
  def shortest_path(graph)
    ports = prompt_route
    # check if metro exists
    if graph.metros.has_key?(ports[0]) && graph.metros.has_key?(ports[1])
      shortest_route(graph, ports)
    else
      puts 'Invalid Input'
    end
  end

  # method which determines and returns the longest flight
  # in CSAir's network of flights
  # In addition to this, this method prints out metro and edge info
  # for the cities along the longest route
  # @param graph reference to state of the map
  # @return longest flight on the graph
  def longest_flight(graph)
    # find the vertex corresponding to the longest flight
    longest_flight_vertex = graph.vertices.values.max_by do |vertex|
      vertex.longest_flight.distance
    end
    # print out the info for the starting vertex
    info = longest_flight_vertex.metro.info

    # print out info about the route itself, and the ending vertex
    longest_flight = longest_flight_vertex.longest_flight
    info << longest_flight.info
  end

  # method which determines and returns the shortest flight
  # in CSAir's network of flights
  # In addition to this, this method prints out metro and edge info
  # for the cities along the shortest route
  # @param graph reference to state of the map
  # @return shortest flight on the graph
  def shortest_flight(graph)
    # find the vertex corresponding to the shortest flight
    shortest_flight_vertex = graph.vertices.values.min_by do |vertex|
      vertex.shortest_flight.distance
    end
    # print out the info for the starting vertex
    info = shortest_flight_vertex.metro.info

    # print out info about the route itself, and the ending vertex
    shortest_flight = shortest_flight_vertex.shortest_flight
    info << shortest_flight.info
  end

  # method which determines and returns the average flight
  # distance in CSAir's network of flights
  # @param graph reference to state of the map
  # @return average flight on the graph
  def average_flight(graph)
    sum = 0
    count = 0
    graph.vertices.each do |_, vertex|
      vertex.edges.each do |edge|
        sum += edge.distance
        count += 1
      end
    end
    "Average Distance: #{sum/count} km"
  end

  # method which determines and returns the biggest city
  # by population in CSAir's network
  # In addition to this, this method prints out metro info
  # for that city
  # @param graph reference to state of the map
  # @return biggest metro by population on the graph
  def biggest_metro(graph)
    # find the vertex corresponding to the metro with the
    # largest population
    biggest_metro_vertex = graph.vertices.values.max_by do |vertex|
      vertex.metro.population
    end
    biggest_metro_vertex.metro.info
  end

  # method which determines and returns the smallest city
  # by population in CSAir's network
  # In addition to this, this method prints out metro info
  # for that city
  # @param graph reference to state of the map
  # @return smallest metro by population on the graph
  def smallest_metro(graph)
    # find the vertex corresponding to the metro with the
    # lowest population
    smallest_metro_vertex = graph.vertices.values.min_by do |vertex|
      vertex.metro.population
    end
    smallest_metro_vertex.metro.info
  end

  # method which determines and returns the average city
  # population in CSAir's network
  # @param graph reference to state of the map
  # @return average metro population on the graph
  def average_metro(graph)
    sum = 0
    count = 0
    graph.vertices.each do |_, vertex|
      sum += vertex.metro.population
      count += 1
    end
    "Average Population: #{sum/count}"
  end

  # method which determines the top metros by number of outgoing routes
  # @param n Number of hub cities to find, if a parameter is given
  # (0 otherwise)
  # @param graph reference to state of the map
  # @return string containing list of major CSAir hubs
  def hub_metros(graph, n = 0)
    # set num_metros to 0, or n if a parameter is given
    num_metros = n
    while num_metros <= 0
      puts 'How many hubs would you like to see?'
      num_metros = gets.chomp.to_i
      puts 'Invalid Input' if num_metros <= 0
    end

    # sort vertices in descending order of number of edges
    hubs = graph.vertices.sort_by{|_, vertex| vertex.edges.length}.reverse
    i = 0
    info = ''
    # print metro info for top num_metros metros
    hubs.each do |_, vertex|
      info << vertex.metro.info
      info << "\nNumber of connections: #{vertex.edges.length}\n"
      i += 1
      break if i == num_metros || i == graph.vertices.keys.length
      info << "\n"
    end
    info
  end
end