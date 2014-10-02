# The Graph class defines the CSAir map and allows us to retrieve
# information queried by the user in io.rb
class Graph
  require 'launchy' # needed to launch a web browser with our map url

  # constructor for a graph
  # Graphs in this class are defined by a set of vertices
  # There is no need for a list of edges since each vertex stores
  # its edges in an adjacency list
  # @param vertices Set of vertices which make up the Graph
  def initialize(vertices)
    @vertices = vertices

    # metros will store metro name, code pairs to allow us to
    # determine a metro's name, given its code
    @metros = Hash.new
    @vertices.each do |code, vertex|
      @metros[vertex.metro.name.downcase] = code
    end
  end

  private
  # method which prints out the metro info for the metro
  # corresponding to the code parameter
  # @param code metro code
  def metro_info_code(code)
    @vertices[code].metro.info
  end

  public
  # method which prints out the metro info for all metros
  # in the CSAir graph
  # @return string containing list of metros
  def list_metros
    metros = ''
    @vertices.each{|_, vertex| metros << vertex.metro.name << "\n"}
    metros
  end

  # method which determines whether or not the string metro
  # is a valid city name, and returns the metro info if it is.
  # otherwise, this method prints 'Invalid Input'
  # @param metro string corresponding to a metro name
  # @return string containing metro and connections info
  def metro_info(metro)
    # see if metro is in our metros hash
    if @metros.has_key?(metro)
      code = @metros[metro]
      info = metro_info_code(code) << "\n"
      info << adjacent_metros(code)
    else
      'Invalid Input'
    end
  end

  # method which prints out information regarding all
  # flights out of a specified metro
  # @param code Metro code
  # @return string containing adjacent metros info
  def adjacent_metros(code)
    connections = "\nConnections:\n"
    @vertices[code].edges.each do |edge|
      connections << edge.info_short << "\n"
    end
    connections
  end

  # method which determines and returns the longest flight
  # in CSAir's network of flights
  # In addition to this, this method prints out metro and edge info
  # for the cities along the longest route
  # @return longest flight on the graph
  def longest_flight
    # find the vertex corresponding to the longest flight
    longest_flight_vertex = @vertices.values.max_by do |vertex|
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
  # @return shortest flight on the graph
  def shortest_flight
    # find the vertex corresponding to the shortest flight
    shortest_flight_vertex = @vertices.values.min_by do |vertex|
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
  # @return average flight on the graph
  def average_flight
    sum = 0
    count = 0
    @vertices.each do |_, vertex|
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
  # @return biggest metro by population on the graph
  def biggest_metro
    # find the vertex corresponding to the metro with the
    # largest population
    biggest_metro_vertex = @vertices.values.max_by do |vertex|
      vertex.metro.population
    end
    biggest_metro_vertex.metro.info
  end

  # method which determines and returns the smallest city
  # by population in CSAir's network
  # In addition to this, this method prints out metro info
  # for that city
  # @return smallest metro by population on the graph
  def smallest_metro
    # find the vertex corresponding to the metro with the
    # lowest population
    smallest_metro_vertex = @vertices.values.min_by do |vertex|
      vertex.metro.population
    end
    smallest_metro_vertex.metro.info
  end

  # method which determines and returns the average city
  # population in CSAir's network
  # @return average metro population on the graph
  def average_metro
    sum = 0
    count = 0
    @vertices.each do |_, vertex|
      sum += vertex.metro.population
      count += 1
    end
    "Average Population: #{sum/count}"
  end

  # method which compiles and returns a list of all continents
  # served by CSAir
  # @return string containing list of continents
  def list_continents
    continents = Hash.new
    @vertices.each do |_, vertex|
      continent = vertex.metro.continent
      # only add to the continents hash if we haven't already added
      # this continent
      continents[continent.to_sym] ||= continent
    end

    # print out continents
    continents_string = ''
    continents.each do |_, continent|
      continents_string << continent << "\n"
    end
    continents_string
  end

  # method which determines the top metros by number of outgoing routes
  # @param num_metros Number of metros to return
  # @return string containing list of major CSAir hubs
  def hub_metros(num_metros = 10)
    # sort vertices in descending order of number of edges
    hubs = @vertices.sort_by{|_, vertex| vertex.edges.length}.reverse
    i = 0
    info = ''
    # print metro info for top num_metros metros
    hubs.each do |_, vertex|
      info << vertex.metro.info
      info << "\nNumber of connections: #{vertex.edges.length}\n"
      i += 1
      break if i == num_metros || i == @vertices.keys.length
      info << "\n"
    end
    info
  end

  # method which generates a url to gcmap.com for all routes in
  # CSAir's network
  # @return url for gcmap.com with all routes in CSAir's network
  def generate_gcmap_url
    url = 'http://www.gcmap.com/mapui?P='
    first_route = true # flag for adding ',+' between routes
    @vertices.each do |code, vertex|
      code_start = code.to_s # first port
      vertex.edges.each do |edge|
        code_end = edge.vertex.metro.code.to_s # second port
        first_route ? first_route = false : url << ',+'
        url << code_start << '-' << code_end
      end
    end
    url << '&MS=wls&DU=km' # distances in kilometers
  end

  # method which allows us to launch a web browser with the url
  # generated in generate_gcmap_url
  def visualize
    # gem Launchy allows us to open the default browser with a
    # specified url
    Launchy.open(generate_gcmap_url)
  end
end