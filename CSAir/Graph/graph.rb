# The Graph class defines the CSAir map and allows us to retrieve
# information queried by the user in io.rb
# noinspection RubyTooManyMethodsInspection
class Graph
  require 'launchy' # needed to launch a web browser with our map url
  require_relative 'prompt'
  include Prompt

  attr_reader :vertices
  attr_reader :metros

  # constructor for a graph
  # Graphs in this class are defined by a set of vertices
  # There is no need for a list of edges since each vertex stores
  # its edges in an adjacency list
  # @param vertices Set of vertices which make up the Graph
  # note: vertices is a (metro code, vertex) hash
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

  # Method which changes the metro code and updates
  # the vertices and metros hashes
  # @param metro_name = new metro name
  def change_code(metro_name)
    # store old info
    code = @metros[metro_name]
    vertex = @vertices[code]
    metro = vertex.metro

    # set the code
    metro.code = prompt_code

    # remove old metro and vertex hashing
    @metros.delete(metro_name)
    @vertices.delete(code)

    # hash new values
    @metros[metro_name] = metro.code.to_sym
    @vertices[metro.code.to_sym] = vertex
  end

  # Method which changes a metro name and updates
  # the vertices and metros hashes
  # @param metro_name new metro name
  def change_name(metro_name)
    # find and store old metro info
    metro = @vertices[@metros[metro_name]].metro
    old_name = metro.name

    # set new metro name
    metro.name = prompt_name

    # delete old metro hash and replace it with new one
    @metros.delete(old_name.downcase)
    @metros[metro.name.downcase] = metro.code.to_sym

    # return new name
    metro.name.downcase
  end

  # Interface for editing a metro
  # Allows a user to enter new information for each part of a metro
  # @param metro_name downcase name of a metro
  def edit_metro(metro_name)
    name = metro_name
    if @metros.has_key?(name)
      metro = @vertices[@metros[name]].metro
      back = false
      # loop until user opts to quit the program
      until back do
        input = prompt_edit_metro
        case input
          when 'b'           then back = true
          when 'code'        then change_code(name)
          #   reset name for future iterations
          when 'name'        then name = change_name(name)
          when 'country'     then metro.country = prompt_country
          when 'continent'   then metro.continent = prompt_continent
          when 'timezone'    then metro.timezone = prompt_timezone
          when 'coordinates' then metro.coordinates = prompt_coordinates
          when 'population'  then metro.population = prompt_population
          when 'region'      then metro.region = prompt_region
          else                    puts 'Invalid Input'
        end
      end
    else
      puts 'Invalid Input'
    end
  end

  # Method which determines if a route is valid
  # @param ports list of metro names
  # @param input user input (next metro name)
  # @param count number of metros
  def valid_route(ports, input, count)
    if @metros.has_key?(input)
      return true if count == 0 # valid since no previous metro
      code_start = @metros[ports[count-1]]
      code_end   = @metros[input]
      route_array = @vertices[code_start].find_route(code_end)
      # make sure that there exists a route from ports[count-1] to input
      route_array.length > 0
    else
      false
    end
  end

  # Method which compiles a list of edges corresponding to a list
  # of metro names
  # @param ports list of metro names
  # @return edges list of edges
  def edge_list(ports)
    edges = Array.new

    (1...ports.length).each { |i|
      code_start = @metros[ports[i-1]]
      code_end = @metros[ports[i]]
      # find_route returns an array of length 1
      # add that single route to edges
      edges << @vertices[code_start].find_route(code_end)[0]
    }
    edges
  end

  # Determines the cost of each km for a given leg
  # Each leg is .05 cheaper per km
  # @param leg_count flight number
  # @return cost per km
  def leg_cost(leg_count)
    cost = 0.35 - (0.05 * leg_count)
    cost = 0.0 if cost < 0 # can't be less than $0 per km
    cost
  end

  # prints out the time (in hours) so that it gives the
  # number of hours and minutes
  def time_formatted(time)
    hours = time.truncate
    minutes = ((time - hours) * 60).round
    string = "#{hours} hour"
    string << 's' unless hours == 1
    string << " and #{minutes} minute"
    string << 's' unless minutes == 1
    string
  end

  public

  # Method which removes a metro from the network
  # @param metro name of the metro to be removed
  def remove_metro(metro)
    # check if metro exists
    if @metros.has_key?(metro)
      code = @metros[metro]

      # accumulate a list of reachable vertices
      destinations = @vertices[code].edges.collect { |edge| edge.vertex }

      # remove metro from the destinations routes
      destinations.each do |vertex|
        vertex.edges.delete_if do |edge|
          edge.vertex.metro.code == code.to_s
        end
      end

      # lastly, remove metro from the vertices and metros hashes
      metro_name = @vertices[code].metro.name
      @vertices.delete(code)
      @metros.delete(metro)
      puts "Removed #{metro_name}\n"
    else
      puts 'Invalid Input'
    end
  end

  # Method which removes a route between 2 metros
  # @param ports Array of length 2 with 2 metro names
  def remove_route(ports)
    # check if metros exist
    if @metros.has_key?(ports[0]) && @metros.has_key?(ports[1])
      code_start = @metros[ports[0]]
      code_end = @metros[ports[1]]

      metro_name_start = @vertices[code_start].metro.name
      metro_name_end   = @vertices[code_end].metro.name

      @vertices[code_start].edges.delete_if do |edge|
        edge.vertex.metro.code == code_end.to_s
      end
      puts "Removed route from #{metro_name_start} to #{metro_name_end}\n"
    else
      puts 'Invalid Input'
    end
  end

  # Given a hash with metro info, this method adds a new metro
  # to the CSAir network
  # @param metro_hash Hash containing metro information
  def add_metro(metro_hash)
    code = metro_hash['code'].to_sym
    @vertices[code] = Vertex.new(Metro.new(metro_hash))
    @metros[metro_hash['name'].downcase] = code
    puts "Added #{@vertices[code].metro.name}\n"
  end

  # Adds a route to the network
  # @param ports array of length 2 containing 2 metro names
  def add_route(ports)
    # check if metros exist
    if @metros.has_key?(ports[0]) && @metros.has_key?(ports[1])
      code_start = @metros[ports[0]]
      code_end = @metros[ports[1]]

      metro_name_start = @vertices[code_start].metro.name
      metro_name_end   = @vertices[code_end].metro.name

      distance = prompt_distance

      # create new edge from first metro to the second
      add_route_with_distance(code_start, code_end, distance)
      puts "Added route from #{metro_name_start} to #{metro_name_end}\n"
    else
      puts 'Invalid Input'
    end
  end

  # method used for testing the functionality of adding routes
  # @param code_start Start metro code
  # @param code_end End metro code
  # @param distance edge length
  def add_route_with_distance(code_start, code_end, distance)
    route = Edge.new(@vertices[code_end], distance)
    @vertices[code_start].add_destination(route)
  end

  # Method which calculates the cost of traversing over a series of edges
  # @param edges list of edges
  # @return cost in dollars
  def cost(edges)
    leg_count = 0
    cost = 0.0

    edges.each do |edge|
      cost += leg_cost(leg_count) * edge.distance
      leg_count += 1 # keep track of number of flights
    end
    # return rounded value (ie. 100.00)
    (cost * 100).round / 100.0
  end

  # Method which calculates the distance of multiple edges
  # @param edges list of edges
  # @return distance in km
  def distance(edges)
    distance = 0
    edges.each { |edge| distance += edge.distance }
    distance
  end

  # Method which calculates the amount of time it takes to traverse
  # over a series of edges
  # @param edges list of edges to traverse
  # @return float representing the time in hours
  def time(edges)
    time = 0.0
    edges.each do |edge|
      time += edge.time
      time += edge.vertex.layover
    end
    # subtract the last layover time (we have arrived)
    time - edges[edges.length-1].vertex.layover if edges.length > 0
  end

  # Prints out the list of ports entered and the distance, cost,
  # and time it takes to travel through all of those ports.
  # @param ports list of metros
  def route_details(ports)
    list = "#{@vertices[@metros[ports[0]]].metro.name}"
    edges = edge_list(ports)
    edges.each{|edge| list << ", #{edge.vertex.metro.name}"}
    puts list
    puts "Distance: #{distance(edges)} km"
    puts "Cost: $#{cost(edges)}"
    puts "Time: #{time_formatted(time(edges))}"
    # puts time(edges)
  end

  # Method containing the functionality for editing the network
  # Allows the user to enter each of the edit functions
  def edit
    back = false
    until back
      input = prompt_edit
      case input
        when 'b'            then back = true
        when 'remove city'  then remove_metro(prompt_metro)
        when 'remove route' then remove_route(prompt_route)
        when 'add city'     then add_metro(prompt_metro_info)
        when 'add route'    then add_route(prompt_route)
        when 'edit city'    then edit_metro(prompt_metro)
        else                     puts 'Invalid Input'
      end
    end
  end

  # prompts the user to enter a list of metros
  # @return details of the route including all metros entered
  def route_info
    ports = Array.new # set up list of metro names
    done = false
    count = 0
    # loop until user opts to stop entering metros
    until done
      input = prompt_route_list(count)
      if input == 'd'
        done = true
      else
        # check if the user entered a valid next metro
        if valid_route(ports, input, count)
          ports << input # add input to the list
          count += 1 # keep track of number of valid metros
        else
          puts 'Invalid Input'
        end
      end
    end
    route_details(ports)
  end

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
        # only add ",+" if this isn't the first route
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